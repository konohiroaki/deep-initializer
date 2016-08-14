package io.github.konohiroaki.deepinitializer.swagger;

import java.lang.reflect.Field;

import io.github.konohiroaki.deepinitializer.BaseFieldInitializer;
import io.github.konohiroaki.deepinitializer.LongTypeInitializer;
import io.swagger.annotations.ApiModelProperty;

public class SwaggerLongInitializer extends BaseFieldInitializer<Long> {

    @Override public Long init(Field field) {
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
        if (property != null && !property.example().equals("")) {
            try {
                return Long.valueOf(property.example());
            } catch (NumberFormatException ignored) {
            }
        }
        return new LongTypeInitializer().init(Long.class);
    }
}
