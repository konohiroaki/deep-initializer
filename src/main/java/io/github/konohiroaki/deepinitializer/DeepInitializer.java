package io.github.konohiroaki.deepinitializer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class DeepInitializer {

    public static <T> T initialize(Class<T> clazz) {
        if (clazz.isPrimitive() || TypeUtils.isPrimitiveWrapper(clazz)) {
            return (T) PrimitivePopulator.populate(clazz);
        } else if (TypeUtils.isString(clazz)) {
            return (T) StringPopulator.populate();
        } else if (clazz.isEnum()) {
            return (T) EnumPopulator.populate(clazz);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            return (T) CollectionPopulator.populate(clazz);
        } else if (Map.class.isAssignableFrom(clazz)) {
            return (T) MapPopulator.populate();
        } else {
            return populateField(clazz);
        }
    }

    private static <T> T initField(Field field) {
        Class<?> clazz = field.getType();
        if (clazz.isPrimitive() || TypeUtils.isPrimitiveWrapper(clazz)) {
            return (T) PrimitivePopulator.populate(field);
        } else if (TypeUtils.isString(clazz)) {
            return (T) StringPopulator.populate(field);
        } else if (clazz.isEnum()) {
            return (T) EnumPopulator.populate(field);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            return (T) CollectionPopulator.populate(clazz);
        } else if (Map.class.isAssignableFrom(clazz)) {
            return (T) MapPopulator.populate();
        } else {
            return (T) populateField(field.getType());
        }
    }

    private static <T> T populateField(Class<T> clazz) {
        T value;
        try {
            value = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(clazz + " type not supported");
        }
        Set<Field> fields = getAllFields(clazz);
        for (Field childField : fields) {
            setProperty(value, childField, initField(childField));
        }
        return value;
    }

    private static Set<Field> getAllFields(Class<?> clazz) {
        Set<Field> fields = new HashSet<>(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            fields.addAll(getAllFields(clazz.getSuperclass()));
        }

        return fields.stream()
            .filter(field -> !field.isSynthetic())
            .collect(Collectors.toSet());
    }

    private static void setProperty(Object object, Field field, Object value) {
        boolean access = field.isAccessible();
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Failed to set property to field " + field.getName());
        }
        field.setAccessible(access);
    }
}
