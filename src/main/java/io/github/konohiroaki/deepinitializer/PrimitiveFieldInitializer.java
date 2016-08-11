package io.github.konohiroaki.deepinitializer;

import java.lang.reflect.Field;

import io.swagger.annotations.ApiModelProperty;

class PrimitiveFieldInitializer {

    Object init(Field field) {
        Class<?> clazz = field.getType();
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);

        if (property != null && !property.example().equals("")) {
            String example = property.example();
            if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
                return Boolean.valueOf(example);
            } else if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
                return Byte.valueOf(example);
            } else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
                return Short.valueOf(example);
            } else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
                return Integer.valueOf(example);
            } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
                return Long.valueOf(example);
            } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
                return Float.valueOf(example);
            } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
                return Double.valueOf(example);
            } else {
                throw new IllegalArgumentException(clazz + " type not supported");
            }
        } else {
            return new PrimitiveTypeInitializer().init(clazz);
        }
    }
}
