package io.github.konohiroaki.deepinitializer;

class PrimitiveTypeInitializer {

    private static boolean DEFAULT_BOOLEAN;
    private static byte DEFAULT_BYTE;
    private static short DEFAULT_SHORT;
    private static int DEFAULT_INT;
    private static long DEFAULT_LONG;
    private static float DEFAULT_FLOAT;
    private static double DEFAULT_DOUBLE;

    Object init(Class<?> clazz) {
        if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
            return DEFAULT_BOOLEAN;
        } else if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
            return DEFAULT_BYTE;
        } else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
            return DEFAULT_SHORT;
        } else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
            return DEFAULT_INT;
        } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
            return DEFAULT_LONG;
        } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
            return DEFAULT_FLOAT;
        } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
            return DEFAULT_DOUBLE;
        } else {
            throw new IllegalArgumentException(clazz + " type not supported");
        }
    }
}
