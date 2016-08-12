package io.github.konohiroaki.deepinitializer;

public class IntegerTypeInitializer extends BaseTypeInitializer<Integer> {

    private static int DEFAULT_INTEGER;

    @Override
    public Integer init(Class<Integer> clazz) {
        return DEFAULT_INTEGER;
    }
}
