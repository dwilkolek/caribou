package eu.wilkolek.caribou.model;

import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Function;

import java.util.Map;

public class CaribouExtension extends AbstractExtension {

    private ModelStore store;

    public CaribouExtension(ModelStore store) {
        this.store = store;
    }

    @Override
    public Map<String, Function> getFunctions() {
        return Map.of("ref", new RefExtension(store), "source", new SourceExtension(store));
    }
}
