package io.github.konohiroaki.deepinitializer;

import java.util.HashSet;
import java.util.Set;

public class SetTypeInitializer extends BaseTypeInitializer<Set> {

    @Override public Set init(Class<Set> clazz) {
        return new HashSet<>();
    }
}
