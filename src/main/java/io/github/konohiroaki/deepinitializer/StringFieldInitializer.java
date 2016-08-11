package io.github.konohiroaki.deepinitializer;

import java.lang.reflect.Field;

import io.swagger.annotations.ApiModelProperty;

public class StringFieldInitializer extends BaseFieldInitializer<String> {

    @Override
    public String init(Field field) {
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);

        if (property != null) {
            return property.example();
        } else {
            return new StringTypeInitializer().init(String.class);
        }
    }
}
