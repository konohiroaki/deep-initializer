package io.github.konohiroaki.deepinitializer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class ReflectionUtils {

    static Set<Field> getAllFields(Class<?> clazz) {
        Set<Field> fields = new HashSet<>(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            fields.addAll(getAllFields(clazz.getSuperclass()));
        }

        return fields.stream()
            .filter(field -> !field.isSynthetic())
            .collect(Collectors.toSet());
    }

    static void setProperty(Object object, Field field, Object value) {
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
