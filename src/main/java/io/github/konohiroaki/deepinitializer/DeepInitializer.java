package io.github.konohiroaki.deepinitializer;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DeepInitializer {

    public static <T> T initialize(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T obj = clazz.newInstance();
        for (Field field : getAllFields(clazz)) {
            if (field.getType().isPrimitive()) {
                continue;
            }

            if (String.class.isAssignableFrom(field.getType())) {
                setProperty(obj, field, "");
            } else if (field.getType().isEnum()) {
                setProperty(obj, field, field.getType().getEnumConstants()[0]);
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                setProperty(obj, field, CollectionPopulator.populate(field.getType()));
            } else if (Map.class.isAssignableFrom(field.getType())) {
                setProperty(obj, field, MapPopulator.populate());
            } else {
                setProperty(obj, field, initialize(field.getType()));
            }
        }
        return obj;
    }

    private static Set<Field> getAllFields(Class<?> clazz) {
        Set<Field> fields = new HashSet<>();

        Collections.addAll(fields, clazz.getDeclaredFields());

        if (clazz.getSuperclass() != null) {
            fields.addAll(getAllFields(clazz.getSuperclass()));
        }

        return fields;
    }

    private static void setProperty(Object object, Field field, Object value) throws IllegalAccessException {
        boolean access = field.isAccessible();
        field.setAccessible(true);
        field.set(object, value);
        field.setAccessible(access);
    }
}
