package eu.wilkolek.caribou.model;

import eu.wilkolek.caribou.model.ModelStore;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;

public class SourceExtension implements Function {

    private ModelStore store;

    SourceExtension(ModelStore store) {
        this.store = store;
    }


    @Override
    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        var selfModel = store.getModelByPath(self.getName());
        String source = args.get("l1").toString();
        if (args.get("l2") != null) {
            source = source + "." + args.get("l2");
            if (args.get("l3") != null) {
                source = source + "." + args.get("l3");
                if (args.get("l4") != null) {
                    source = source + "." + args.get("l4");
                }
            }
        }
        selfModel.addSource(source);
        return source;
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("l1", "l2", "l3", "l4");
    }
}
