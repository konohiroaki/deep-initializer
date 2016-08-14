package io.github.konohiroaki.deepinitializer.swagger;

import java.lang.reflect.Field;

import io.github.konohiroaki.deepinitializer.BaseFieldInitializer;
import io.github.konohiroaki.deepinitializer.ShortTypeInitializer;
import io.swagger.annotations.ApiModelProperty;

public class SwaggerShortInitializer extends BaseFieldInitializer<Short> {

    @Override public Short init(Field field) {
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
        if (property != null && !property.example().equals("")) {
            try {
                return Short.valueOf(property.example());
            } catch (NumberFormatException ignored) {
            }
        }
        return new ShortTypeInitializer().init(Short.class);
    }
}
