package io.github.konohiroaki.deepinitializer.swagger;

import java.lang.reflect.Field;

import io.github.konohiroaki.deepinitializer.BaseFieldInitializer;
import io.github.konohiroaki.deepinitializer.DoubleTypeInitializer;
import io.swagger.annotations.ApiModelProperty;

public class SwaggerDoubleInitializer extends BaseFieldInitializer<Double> {

    @Override public Double init(Field field) {
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
        if (property != null && !property.example().equals("")) {
            try {
                return Double.valueOf(property.example());
            } catch (NumberFormatException ignored) {
            }
        }
        return new DoubleTypeInitializer().init(Double.class);
    }
}
