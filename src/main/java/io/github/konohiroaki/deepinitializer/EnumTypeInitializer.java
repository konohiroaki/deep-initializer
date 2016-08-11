package io.github.konohiroaki.deepinitializer;


class EnumTypeInitializer extends BaseTypeInitializer<Enum> {

    @Override
    public Enum<?> init(Class<Enum> clazz) {
        return clazz.getEnumConstants()[0];
    }
}
