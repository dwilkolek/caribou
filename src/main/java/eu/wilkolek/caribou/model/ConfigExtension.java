package eu.wilkolek.caribou.model;

import eu.wilkolek.caribou.materilization.Materializer;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;

public class ConfigExtension implements Function {

    private ModelStore store;

    ConfigExtension(ModelStore store) {
        this.store = store;
    }


    @Override
    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        Class clazz = null;
        try {
            clazz = Class.forName(args.get("materializer").toString());
            var materializer = clazz.getDeclaredConstructor().newInstance();
            if (materializer instanceof Materializer) {
                store.getModel(self.getName()).setMaterializer((Materializer) materializer);
            } else {
                throw new IllegalArgumentException("Materializer not found");
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }


        return  "-- Using materializer: " + clazz.getName() + "\n";
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("materializer");
    }
}
