package eu.wilkolek.caribou;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Caribou {
    String path = "/Users/dwilkolek/dev/caribou/sql_model";

    public ModelStore readModel() throws IOException {
        var modelStore = new ModelStore();
        try (Stream<Path> stream = Files.walk(Path.of(path))) {
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

}
