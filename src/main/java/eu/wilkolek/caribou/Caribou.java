package eu.wilkolek.caribou;

import eu.wilkolek.caribou.execution.ExecutionStepRunner;
import eu.wilkolek.caribou.model.CaribouExtension;
import eu.wilkolek.caribou.model.Model;
import eu.wilkolek.caribou.model.ModelStore;
import io.pebbletemplates.pebble.PebbleEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Caribou {

    private final PebbleEngine engine;
    private final ModelStore store = new ModelStore();

    Caribou() {
        this(new PebbleEngine.Builder());
    }
    Caribou(PebbleEngine.Builder engine) {
        this.engine = engine.extension(new CaribouExtension(store)).build();
    }

    public void run(Path directory, ExecutionStepRunner stepRunner) throws IOException {
        try (Stream<Path> stream = Files.walk(directory)) {
            stream.filter(Files::isRegularFile).forEach(path -> {
                store.addModel(new Model(path));
            });
        }

        var plan = store.compileToExecutionPlan(engine);
        plan.execute(stepRunner);
    }

}
