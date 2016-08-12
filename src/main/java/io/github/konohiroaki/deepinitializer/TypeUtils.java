package io.github.konohiroaki.deepinitializer;

class TypeUtils {

    static boolean isAutoboxable(Class<?> from, Class<?> to) {
        return from == boolean.class && to == Boolean.class
               || from == byte.class && to == Byte.class
               || from == char.class && to == Character.class
               || from == short.class && to == Short.class
               || from == int.class && to == Integer.class
               || from == long.class && to == Long.class
               || from == float.class && to == Float.class
               || from == double.class && to == Double.class;
    }
}
