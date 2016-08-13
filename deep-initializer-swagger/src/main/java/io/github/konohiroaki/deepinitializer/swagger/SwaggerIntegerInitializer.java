package io.github.konohiroaki.deepinitializer.swagger;

import java.lang.reflect.Field;

import io.github.konohiroaki.deepinitializer.BaseFieldInitializer;
import io.github.konohiroaki.deepinitializer.IntegerTypeInitializer;
import io.swagger.annotations.ApiModelProperty;

public class SwaggerIntegerInitializer extends BaseFieldInitializer<Integer> {

    @Override public Integer init(Field field) {
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
        if (property != null && !property.example().equals("")) {
            try {
                return Integer.valueOf(property.example());
            } catch (NumberFormatException ignored) {
            }
        }
        return new IntegerTypeInitializer().init(Integer.class);
    }
}
