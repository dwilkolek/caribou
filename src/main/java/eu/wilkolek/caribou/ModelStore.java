package eu.wilkolek.caribou;

import io.pebbletemplates.pebble.PebbleEngine;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelStore {


    private List<Model> models = new ArrayList<>();

    void addModel(Model model) {
        this.models.add(model);
    }

    Model getModel(String name) {
        return models.stream().filter(model -> model.name.equals(name)).findFirst().orElseThrow();
    }

    Model getModelByPath(String path) {
        return models.stream().filter(model -> model.path.equals(path)).findFirst().orElseThrow();
    }

    void compileModels(PebbleEngine engine) throws IOException {
        for (Model model : models) {
            model.compile(engine);
        }
    }

}
