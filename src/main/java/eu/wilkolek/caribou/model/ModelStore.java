package eu.wilkolek.caribou.model;

import io.pebbletemplates.pebble.PebbleEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class ModelStore {

    public static ModelStore readModel(Path directory) throws IOException {
        var modelStore = new ModelStore();
        try (Stream<Path> stream = Files.walk(directory)) {
            stream.filter(Files::isRegularFile).forEach(path -> {
                modelStore.addModel(new Model(path));
            });
        }

        PebbleEngine engine = new PebbleEngine.Builder()
                .extension(new CaribouExtension(modelStore))
                .build();


        modelStore.compileModels(engine);

        return modelStore;
    }

    private List<Model> models = new ArrayList<>();

    void addModel(Model model) {
        this.models.add(model);
    }

    Model getModel(String name) {
        return models.stream().filter(model -> model.name.equals(name)).findFirst().orElseThrow();
    }

    public Model getModelByPath(String path) {
        return models.stream().filter(model -> model.path.equals(path)).findFirst().orElseThrow();
    }

    void compileModels(PebbleEngine engine) throws IOException {
        for (Model model : models) {
            model.compile(engine);
        }
    }

    public List<Model> getModels() {
        return Collections.unmodifiableList(models);
    }
}
