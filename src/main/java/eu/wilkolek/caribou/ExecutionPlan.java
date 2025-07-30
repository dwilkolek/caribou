package eu.wilkolek.caribou;

import eu.wilkolek.caribou.model.Model;
import eu.wilkolek.caribou.model.ModelStore;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ExecutionPlan {

    private List<Model> todo;
    private final HashSet<Model> completed = new HashSet<>();

    public ExecutionPlan(ModelStore store) {
        this.todo = store.getModels();
    }

    Model getNext() {
        if (!hasNext()) {
            return null;
        }
        return this.todo.stream().filter(model -> !completed.contains(model) && completed.containsAll(model.getDependencies())).findFirst().orElse(null);
    }

    boolean hasNext() {
        return todo.size() != completed.size();
    }

    void complete(Model model) {
        completed.add(model);
    }
}
