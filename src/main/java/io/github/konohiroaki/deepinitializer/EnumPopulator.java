package io.github.konohiroaki.deepinitializer;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.swagger.annotations.ApiModelProperty;

class EnumPopulator {

    static Object populate(Class<?> clazz) {
        return clazz.getEnumConstants()[0];
    }

    @SuppressWarnings("unchecked")
    static Object populate(Field field) {
        Class<?> clazz = field.getType();
        ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);

        if (property != null && !property.example().equals("")) {
            String example = property.example();
            Optional<Enum<?>> value = ((List<Enum<?>>) Arrays.asList(clazz.getEnumConstants())).stream()
                .filter(e -> Objects.equals(e.name(), example))
                .findFirst();
            if (value.isPresent()) {
                return value.get();
            }
        }
        return populate(clazz);
    }
}
