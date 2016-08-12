package io.github.konohiroaki.deepinitializer;

public class EnumTypeInitializer extends BaseTypeInitializer<Enum> {

    @Override
    public Enum<?> init(Class<Enum> clazz) {
        return clazz.getEnumConstants()[0];
    }
}
