package eu.wilkolek.caribou.execution;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrintlnModelExecutor implements ExecutionStepRunner {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void execute(ExecutionStep step) {
        var future = executor.submit(() -> {
            var sb = new StringBuilder();
            sb.append("---------------- Executing step: ").append(step.getName()).append(" ----------------\n");
            sb.append("\n");
            sb.append(step.getSql());
            sb.append("\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Sorry mate", e);
            }
            sb.append("---------------- Done step: ").append(step.getName()).append(" ----------------\n");
            System.out.println(sb);
        });

        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }

}