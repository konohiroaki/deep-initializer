package io.github.konohiroaki.deepinitializer.swagger;

import java.lang.reflect.Field;

import io.github.konohiroaki.deepinitializer.BaseFieldInitializer;
import io.github.konohiroaki.deepinitializer.BooleanTypeInitializer;
import io.swagger.annotations.ApiModelProperty;

public class SwaggerBooleanInitializer extends BaseFieldInitializer<Boolean> {

    @Override public Boolean init(Field field) {
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
        if (property != null && !property.example().equals("")) {
            return Boolean.valueOf(property.example());
        }
        return new BooleanTypeInitializer().init(Boolean.class);
    }
}
