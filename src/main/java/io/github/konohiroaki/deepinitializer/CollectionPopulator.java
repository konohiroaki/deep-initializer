package io.github.konohiroaki.deepinitializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CollectionPopulator {

    static Collection<?> populate(Class<?> type) {
        Collection<?> collection = new ArrayList<>();

        if (type.isAssignableFrom(List.class)) {
            collection = new ArrayList<>();
        } else if (type.isAssignableFrom(Set.class)) {
            collection = new HashSet<>();
        }

        return collection;
    }
}
