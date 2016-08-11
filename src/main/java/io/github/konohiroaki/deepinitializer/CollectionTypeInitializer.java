package io.github.konohiroaki.deepinitializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CollectionTypeInitializer extends BaseTypeInitializer<Collection> {

    @Override
    public Collection<?> init(Class<Collection> clazz) {
        if (clazz.isAssignableFrom(List.class)) {
            return new ArrayList<>();
        } else if (clazz.isAssignableFrom(Set.class)) {
            return new HashSet<>();
        } else {
            throw new IllegalArgumentException(clazz + " type not supported");
        }
    }
}
