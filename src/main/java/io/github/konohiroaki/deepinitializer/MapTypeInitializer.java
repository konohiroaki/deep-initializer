package io.github.konohiroaki.deepinitializer;

import java.util.HashMap;
import java.util.Map;

class MapTypeInitializer extends BaseTypeInitializer<Map> {

    @Override
    public Map<?, ?> init(Class<Map> clazz) {
        return new HashMap<>();
    }
}
