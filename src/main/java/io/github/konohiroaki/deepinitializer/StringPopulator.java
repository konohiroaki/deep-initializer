package io.github.konohiroaki.deepinitializer;

import java.lang.reflect.Field;

import io.swagger.annotations.ApiModelProperty;

class StringPopulator {

    private static final String EMPTY = "";

    static String populate() {
        return EMPTY;
    }

    static String populate(Field field) {
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);

        if (property != null) {
            return property.example();
        } else {
            return populate();
        }
    }
}
