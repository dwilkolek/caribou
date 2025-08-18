package eu.wilkolek.caribou;

import eu.wilkolek.caribou.execution.ExecutionStep;
import eu.wilkolek.caribou.execution.ExecutionStepRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ExecutionPlan {

    private List<ExecutionStep> steps = new ArrayList<>();


    public ExecutionPlan() {
    }

    synchronized public void addStep(ExecutionStep step) {
        steps.add(step);
    }

    synchronized public void execute(ExecutionStepRunner stepRunner) {

        var stepFutures = new HashMap<String, CompletableFuture<Void>>();
        for (ExecutionStep step : steps) {
            var future = new CompletableFuture<Void>();
            stepFutures.put(step.getName(), future);
        }
        ArrayList<CompletableFuture<Void>> allFutures = new ArrayList<>(stepFutures.values());

        for (ExecutionStep step : steps) {
            var dependencies = step.getDependencies().stream().toList();
            CompletableFuture[] stepDependencyFutures = new CompletableFuture[dependencies.size()];
            for (int i = 0; i < dependencies.size(); i++) {
                stepDependencyFutures[i] = stepFutures.get(dependencies.get(i));
            }
            CompletableFuture<Void> allDone = CompletableFuture.allOf(stepDependencyFutures);
            allFutures.add(allDone.whenComplete((result, throwable) -> {
                stepRunner.execute(step);
                stepFutures.get(step.getName()).complete(null);
            }));
        }
        CompletableFuture[] allFuturesArr = new CompletableFuture[allFutures.size()];
        CompletableFuture.anyOf(allFutures.toArray(allFuturesArr)).join();

        stepRunner.shutdown();
    }
}
