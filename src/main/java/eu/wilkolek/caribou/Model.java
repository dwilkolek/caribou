package eu.wilkolek.caribou;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Model {

    private List<Model> dependencies =  new ArrayList<>();
    String path;
    String name;

    String compiledSql;


    public Model(Path path) {
//        this.isDirectory = Files.isDirectory(path);
        this.path = path.toAbsolutePath().toString();
        this.name = path.getFileName().toString().toLowerCase(Locale.ROOT).replace(".sql", "");
    }

    public void addDependency(Model model) {
        dependencies.add(model);
    }


    public void compile(PebbleEngine engine) throws IOException {
        PebbleTemplate compiledTemplate = engine.getTemplate(path);

        Map<String, Object> context = new HashMap<>();
        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);

        this.compiledSql = writer.toString();
    }

}
