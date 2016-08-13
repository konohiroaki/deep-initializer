package io.github.konohiroaki.deepinitializer;

public class ShortTypeInitializer extends BaseTypeInitializer<Short> {

    private static short DEFAULT_SHORT;

    @Override
    public Short init(Class<Short> clazz) {
        return DEFAULT_SHORT;
    }
}
