package io.github.konohiroaki.deepinitializer;

import java.util.HashMap;
import java.util.Map;

public class MapTypeInitializer extends BaseTypeInitializer<Map> {

    @Override
    public Map<?, ?> init(Class<Map> clazz) {
        return new HashMap<>();
    }
}
