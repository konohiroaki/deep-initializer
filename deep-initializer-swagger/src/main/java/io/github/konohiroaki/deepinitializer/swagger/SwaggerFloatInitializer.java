package io.github.konohiroaki.deepinitializer.swagger;

import java.lang.reflect.Field;

import io.github.konohiroaki.deepinitializer.BaseFieldInitializer;
import io.github.konohiroaki.deepinitializer.FloatTypeInitializer;
import io.swagger.annotations.ApiModelProperty;

public class SwaggerFloatInitializer extends BaseFieldInitializer<Float> {

    @Override public Float init(Field field) {
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
        if (property != null && !property.example().equals("")) {
            try {
                return Float.valueOf(property.example());
            } catch (NumberFormatException ignored) {
            }
        }
        return new FloatTypeInitializer().init(Float.class);
    }
}
