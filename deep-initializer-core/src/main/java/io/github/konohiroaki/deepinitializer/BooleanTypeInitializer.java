package io.github.konohiroaki.deepinitializer;

public class BooleanTypeInitializer extends BaseTypeInitializer<Boolean> {

    private static boolean DEFAULT_BOOLEAN;

    @Override
    public Boolean init(Class<Boolean> clazz) {
        return DEFAULT_BOOLEAN;
    }
}
