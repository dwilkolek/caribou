package eu.wilkolek.caribou;

import eu.wilkolek.caribou.model.Model;
import eu.wilkolek.caribou.model.ModelStore;
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

    private Path directory;

    public Caribou(Path directory) {
        this.directory = directory;
    }

    public void execute() throws IOException {
        var store = ModelStore.readModel(directory);
        ExecutionPlan plan = new ExecutionPlan(store);
        new SingleThreadModelExecutor(plan).execute();
    }


}
