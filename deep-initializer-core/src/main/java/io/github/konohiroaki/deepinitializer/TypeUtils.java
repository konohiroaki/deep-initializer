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

    static boolean isDefaultInitializer(BaseTypeInitializer<?> value) {
        return value.getClass() == BooleanTypeInitializer.class
               || value.getClass() == ByteTypeInitializer.class
               || value.getClass() == ByteTypeInitializer.class
               || value.getClass() == CharacterTypeInitializer.class
               || value.getClass() == ShortTypeInitializer.class
               || value.getClass() == IntegerTypeInitializer.class
               || value.getClass() == LongTypeInitializer.class
               || value.getClass() == FloatTypeInitializer.class
               || value.getClass() == DoubleTypeInitializer.class
               || value.getClass() == StringTypeInitializer.class
               || value.getClass() == EnumTypeInitializer.class
               || value.getClass() == ListTypeInitializer.class
               || value.getClass() == SetTypeInitializer.class
               || value.getClass() == MapTypeInitializer.class;
    }
}
