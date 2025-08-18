package eu.wilkolek.caribou.model;

import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.util.List;
import java.util.Map;

public class RefExtension implements Function {

    private ModelStore store;

    public RefExtension(ModelStore store) {
        this.store = store;
    }


    @Override
    public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
        var selfModel = store.getModelByPath(self.getName());
        var dependencyModel = store.getModel(args.get("model").toString());
        selfModel.addDependency(dependencyModel);
        return args.get("model");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("model");
    }
}
