package io.github.konohiroaki.deepinitializer;

public class LongTypeInitializer extends BaseTypeInitializer<Long> {

    private static long DEFAULT_LONG;

    @Override
    public Long init(Class<Long> clazz) {
        return DEFAULT_LONG;
    }
}
