package io.github.konohiroaki.deepinitializer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            return populateFields(clazz);
        }
    }

    public static <T> List<T> initialize(Class<T> clazz, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size needs to be positive number but was " + size);
        }

        return Stream.generate(() -> initialize(clazz)).limit(size).collect(Collectors.toList());
    }

    private static <T> T populateFields(Class<T> clazz) {
        T value;
        try {
            value = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(clazz + " type not supported");
        }
        Set<Field> fields = getAllFields(clazz);
        for (Field childField : fields) {
            setProperty(value, childField, populateField(childField));
        }
        return value;
    }

    private static <T> T populateField(Field field) {
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
            return (T) populateFields(clazz);
        }
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
