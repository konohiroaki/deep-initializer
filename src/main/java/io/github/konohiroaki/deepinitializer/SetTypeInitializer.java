package io.github.konohiroaki.deepinitializer;

import java.util.HashSet;
import java.util.Set;

class SetTypeInitializer extends BaseTypeInitializer<Set> {

    @Override public Set init(Class<Set> clazz) {
        return new HashSet<>();
    }
}
