package io.github.konohiroaki.deepinitializer;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class DeepInitializer {

    private final Map<Class<?>, BaseTypeInitializer<?>> typeInitializerMap = new HashMap<>();
    private final Map<Class<?>, BaseFieldInitializer<?>> fieldInitializerMap = new HashMap<>();

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
        typeInitializerMap.put(type, init);
    }

    public void removeTypeInitializer(Class<?> type) {
        typeInitializerMap.remove(type);
    }

    public <T> void addFieldInitializer(Class<? extends T> type, BaseFieldInitializer<T> init) {
        fieldInitializerMap.put(type, init);
    }

    public void removeFieldInitializer(Class<?> type) {
        fieldInitializerMap.remove(type);
    }

    public <T> T init(Class<T> clazz) {
        if (clazz.isEnum()) {
            return (T) typeInitializerMap.get(Enum.class).init((Class) clazz);
        }
        for (Map.Entry<Class<?>, BaseTypeInitializer<?>> entry : typeInitializerMap.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                return (T) entry.getValue().init((Class) clazz);
            }
        }

        if (TypeUtils.isPrimitive(clazz) || TypeUtils.isPrimitiveWrapper(clazz)) {
            return (T) new PrimitiveInitializer().populate(clazz);
        } else {
            return initFields(clazz);
        }
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
        for (Field childField : fields) {
            ReflectionUtils.setProperty(value, childField, initField(childField));
        }
        return value;
    }

    private <T> T initField(Field field) {
        Class<?> clazz = field.getType();
        if (clazz.isEnum()) {
            return (T) fieldInitializerMap.get(Enum.class).init(field);
        }
        for (Map.Entry<Class<?>, BaseFieldInitializer<?>> entry : fieldInitializerMap.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                return (T) entry.getValue().init(field);
            }
        }
        for (Map.Entry<Class<?>, BaseTypeInitializer<?>> entry : typeInitializerMap.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                return (T) entry.getValue().init((Class) clazz);
            }
        }

        if (clazz.isPrimitive() || TypeUtils.isPrimitiveWrapper(clazz)) {
            return (T) new PrimitiveInitializer().populate(field);
        } else {
            return (T) initFields(clazz);
        }
    }
}
