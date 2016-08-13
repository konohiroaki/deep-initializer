package io.github.konohiroaki.deepinitializer.swagger;

import java.lang.reflect.Field;

import io.github.konohiroaki.deepinitializer.BaseFieldInitializer;
import io.github.konohiroaki.deepinitializer.CharacterTypeInitializer;
import io.swagger.annotations.ApiModelProperty;

public class SwaggerCharacterInitializer extends BaseFieldInitializer<Character> {

    @Override public Character init(Field field) {
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
        if (property != null && property.example().length() == 1) {
            return property.example().charAt(0);
        }
        return new CharacterTypeInitializer().init(Character.class);
    }
}
