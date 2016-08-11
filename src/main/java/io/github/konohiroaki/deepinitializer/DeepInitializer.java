package io.github.konohiroaki.deepinitializer;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class DeepInitializer {

    private final List<Map.Entry<Class<?>, BaseTypeInitializer<?>>> typeInitializer = new ArrayList<>();
    private final List<Map.Entry<Class<?>, BaseFieldInitializer<?>>> fieldInitializer = new ArrayList<>();

    public DeepInitializer() {
        addDefaultTypeInitializer();
        addDefaultFieldInitializer();
    }

    private void addDefaultTypeInitializer() {
        addTypeInitializer(String.class, new StringTypeInitializer());
        addTypeInitializer(Enum.class, new EnumTypeInitializer());
        addTypeInitializer(Collection.class, new CollectionTypeInitializer());
        addTypeInitializer(Map.class, new MapTypeInitializer());
    }

    private void addDefaultFieldInitializer() {
        addFieldInitializer(String.class, new StringFieldInitializer());
        addFieldInitializer(Enum.class, new EnumFieldInitializer());
    }

    public <T> void addTypeInitializer(Class<? extends T> type, BaseTypeInitializer<T> init) {
        typeInitializer.add(new AbstractMap.SimpleImmutableEntry<>(type, init));
    }

    public void removeTypeInitializer(Class<?> type) {
        Map.Entry<Class<?>, BaseTypeInitializer<?>> target = null;
        for (Map.Entry<Class<?>, BaseTypeInitializer<?>> entry : typeInitializer) {
            if (entry.getKey() == type) {
                target = entry;
                break;
            }
        }
        if (target != null) {
            typeInitializer.remove(target);
        }
    }

    public <T> void addFieldInitializer(Class<? extends T> type, BaseFieldInitializer<T> init) {
        fieldInitializer.add(new AbstractMap.SimpleImmutableEntry<>(type, init));
    }

    public void removeFieldInitializer(Class<?> type) {
        Map.Entry<Class<?>, BaseFieldInitializer<?>> target = null;
        for (Map.Entry<Class<?>, BaseFieldInitializer<?>> entry : fieldInitializer) {
            if (entry.getKey() == type) {
                target = entry;
                break;
            }
        }
        if (target != null) {
            fieldInitializer.remove(target);
        }
    }

    public <T> T init(Class<T> clazz) {
        T value = getTypeValue(clazz);
        if (value != null) {
            return value;
        }

        return initFields(clazz);
    }

    public <T> List<T> init(Class<T> clazz, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size needs to be positive number but was " + size);
        }

        return Stream.generate(() -> init(clazz)).limit(size).collect(Collectors.toList());
    }

    private <T> T initFields(Class<T> clazz) {
        T value;
        try {
            value = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(clazz + " type not supported");
        }

        Set<Field> fields = ReflectionUtils.getAllFields(clazz);
        for (Field field : fields) {
            ReflectionUtils.setProperty(value, field, initField(field));
        }
        return value;
    }

    private <T> T initField(Field field) {
        T value = getFieldValue((field));
        if (value != null) {
            return value;
        }

        return initFields((Class<T>) field.getType());
    }

    private <T> T getTypeValue(Class<T> clazz) {
        for (Map.Entry<Class<?>, BaseTypeInitializer<?>> entry : Lists.reverse(typeInitializer)) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                return (T) entry.getValue().init((Class) clazz);
            }
        }
        if (TypeUtils.isPrimitive(clazz) || TypeUtils.isPrimitiveWrapper(clazz)) {
            return (T) new PrimitiveInitializer().init(clazz);
        }
        return null;
    }

    private <T> T getFieldValue(Field field) {
        Class<T> clazz = (Class<T>) field.getType();
        for (Map.Entry<Class<?>, BaseFieldInitializer<?>> entry : Lists.reverse(fieldInitializer)) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                return (T) entry.getValue().init(field);
            }
        }
        if (TypeUtils.isPrimitive(clazz) || TypeUtils.isPrimitiveWrapper(clazz)) {
            return (T) new PrimitiveInitializer().init(field);
        }
        return getTypeValue(clazz);
    }
}
