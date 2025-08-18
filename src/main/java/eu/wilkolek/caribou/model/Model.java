package eu.wilkolek.caribou.model;

import eu.wilkolek.caribou.execution.ExecutionStep;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Model {

    private final List<Model> dependencies = new ArrayList<>();
    private final List<String> sources = new ArrayList<>();
    String path;
    public String name;

    public Model(Path path) {
        this.path = path.toAbsolutePath().toString();
        this.name = path.getFileName().toString().toLowerCase(Locale.ROOT).replace(".sql", "");
    }

    public void addDependency(Model model) {
        dependencies.add(model);
    }

    public void addSource(String source) {
        sources.add(source);
    }

    public List<Model> getDependencies() {
        return Collections.unmodifiableList(dependencies);
    }

    public ExecutionStep compileToStep(PebbleEngine engine) throws IOException {
        PebbleTemplate compiledTemplate = engine.getTemplate(path);

        Map<String, Object> context = new HashMap<>();
        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);

        return new ExecutionStep(
                this.name,
                new HashSet<>(this.dependencies.stream().map(model -> model.name).collect(Collectors.toSet())),
                writer.toString()
        );
    }

}
