package io.github.konohiroaki.deepinitializer.swagger;

import java.lang.reflect.Field;

import io.github.konohiroaki.deepinitializer.BaseFieldInitializer;
import io.github.konohiroaki.deepinitializer.ByteTypeInitializer;
import io.swagger.annotations.ApiModelProperty;

public class SwaggerByteInitializer extends BaseFieldInitializer<Byte> {

    @Override public Byte init(Field field) {
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
        if (property != null && !property.example().equals("")) {
            try {
                return Byte.valueOf(property.example());
            } catch (NumberFormatException ignored) {
            }
        }
        return new ByteTypeInitializer().init(Byte.class);
    }
}
