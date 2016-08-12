package io.github.konohiroaki.deepinitializer;

public class StringTypeInitializer extends BaseTypeInitializer<String> {

    private static final String EMPTY = "";

    @Override
    public String init(Class<String> clazz) {
        return EMPTY;
    }
}
