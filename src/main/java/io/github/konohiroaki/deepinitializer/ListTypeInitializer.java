package io.github.konohiroaki.deepinitializer;

import java.util.ArrayList;
import java.util.List;

class ListTypeInitializer extends BaseTypeInitializer<List> {

    @Override
    public List<?> init(Class<List> clazz) {
        return new ArrayList<>();
    }
}
