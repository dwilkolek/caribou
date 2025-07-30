package eu.wilkolek.caribou.model;

import eu.wilkolek.caribou.materilization.Materializer;
import eu.wilkolek.caribou.materilization.SnowflakeIncrementalMaterializer;
import eu.wilkolek.caribou.materilization.TableMaterializer;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.*;

public class Model {

    private List<Model> dependencies = new ArrayList<>();
    private List<String> sources = new ArrayList<>();
    String path;
    public String name;

    public String compiledSql;
    public Materializer materializer = new TableMaterializer();


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

    public void compile(PebbleEngine engine) throws IOException {
        PebbleTemplate compiledTemplate = engine.getTemplate(path);

        Map<String, Object> context = new HashMap<>();
        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);

        this.compiledSql = writer.toString();
    }

    public void setMaterializer(Materializer materializer) {
        this.materializer = materializer;
    }

    public void materialize() {
        this.materializer.materialize(this);
    }
}
