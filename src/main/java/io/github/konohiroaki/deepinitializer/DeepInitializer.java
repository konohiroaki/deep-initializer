package io.github.konohiroaki.deepinitializer;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
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
    }

    private void addDefaultTypeInitializer() {
        addTypeInitializer(Boolean.class, new BooleanTypeInitializer());
        addTypeInitializer(Byte.class, new ByteTypeInitializer());
        addTypeInitializer(Character.class, new CharacterTypeInitializer());
        addTypeInitializer(Short.class, new ShortTypeInitializer());
        addTypeInitializer(Integer.class, new IntegerTypeInitializer());
        addTypeInitializer(Long.class, new LongTypeInitializer());
        addTypeInitializer(Float.class, new FloatTypeInitializer());
        addTypeInitializer(Double.class, new DoubleTypeInitializer());
        addTypeInitializer(String.class, new StringTypeInitializer());
        addTypeInitializer(Enum.class, new EnumTypeInitializer());
        addTypeInitializer(List.class, new ListTypeInitializer());
        addTypeInitializer(Set.class, new SetTypeInitializer());
        addTypeInitializer(Map.class, new MapTypeInitializer());
    }

    public <T> void addTypeInitializer(Class<? extends T> type, BaseTypeInitializer<T> init) {
        typeInitializer.add(new AbstractMap.SimpleImmutableEntry<>(type, init));
    }

    public void removeTypeInitializer(Class<?> type) {
        List<Map.Entry<Class<?>, BaseTypeInitializer<?>>> target = new ArrayList<>();
        for (Map.Entry<Class<?>, BaseTypeInitializer<?>> entry : typeInitializer) {
            if (entry.getKey() == type) {
                target.add(entry);
            }
        }
        typeInitializer.removeAll(target);
    }

    public <T> void addFieldInitializer(Class<? extends T> type, BaseFieldInitializer<T> init) {
        fieldInitializer.add(new AbstractMap.SimpleImmutableEntry<>(type, init));
    }

    public void removeFieldInitializer(Class<?> type) {
        List<Map.Entry<Class<?>, BaseFieldInitializer<?>>> target = new ArrayList<>();
        for (Map.Entry<Class<?>, BaseFieldInitializer<?>> entry : fieldInitializer) {
            if (entry.getKey() == type) {
                target.add(entry);
            }
        }
        fieldInitializer.removeAll(target);
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

        for (Field field : ReflectionUtils.getAllFields(clazz)) {
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
        for (Map.Entry<Class<?>, BaseTypeInitializer<?>> entry : ListUtils.reverse(typeInitializer)) {
            if (entry.getKey().isAssignableFrom(clazz) || TypeUtils.isAutoboxable(clazz, entry.getKey())) {
                return (T) entry.getValue().init((Class) clazz);
            }
        }
        return null;
    }

    private <T> T getFieldValue(Field field) {
        Class<T> clazz = (Class<T>) field.getType();
        for (Map.Entry<Class<?>, BaseFieldInitializer<?>> entry : ListUtils.reverse(fieldInitializer)) {
            if (entry.getKey().isAssignableFrom(clazz) || TypeUtils.isAutoboxable(clazz, entry.getKey())) {
                return (T) entry.getValue().init(field);
            }
        }
        return getTypeValue(clazz);
    }
}
