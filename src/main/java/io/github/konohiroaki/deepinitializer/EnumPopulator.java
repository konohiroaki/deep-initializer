package io.github.konohiroaki.deepinitializer;


class EnumPopulator {

    static Object populate(Class<?> clazz) {
        return clazz.getEnumConstants()[0];
    }
}
