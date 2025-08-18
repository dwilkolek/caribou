package eu.wilkolek.caribou.model;

import eu.wilkolek.caribou.ExecutionPlan;
import io.pebbletemplates.pebble.PebbleEngine;

import java.io.IOException;
import java.util.*;

public class ModelStore {

    private List<Model> models = new ArrayList<>();

    public void addModel(Model model) {
        this.models.add(model);
    }

    Model getModel(String name) {
        return models.stream().filter(model -> model.name.equals(name)).findFirst().orElseThrow();
    }

    public Model getModelByPath(String path) {
        return models.stream().filter(model -> model.path.equals(path)).findFirst().orElseThrow();
    }

    public ExecutionPlan compileToExecutionPlan(PebbleEngine engine) throws IOException {
        var executionPlan = new ExecutionPlan();
        for (Model model : models) {
            var step = model.compileToStep(engine);
            executionPlan.addStep(step);
        }
        return executionPlan;
    }

}
