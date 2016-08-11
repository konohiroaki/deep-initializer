package io.github.konohiroaki.deepinitializer;

class TypeUtils {

    static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive();
    }

    static boolean isPrimitiveWrapper(Class<?> clazz) {
        return clazz == Double.class || clazz == Float.class
               || clazz == Long.class || clazz == Integer.class
               || clazz == Short.class || clazz == Character.class
               || clazz == Byte.class || clazz == Boolean.class;
    }

    static boolean isString(Class<?> clazz) {
        return clazz == String.class;
    }

    static boolean isEnum(Class<?> clazz) {
        return clazz.isEnum();
    }
}
