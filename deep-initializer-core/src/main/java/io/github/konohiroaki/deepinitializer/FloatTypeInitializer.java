package io.github.konohiroaki.deepinitializer;

public class FloatTypeInitializer extends BaseTypeInitializer<Float> {

    private static float DEFAULT_FLOAT;

    @Override
    public Float init(Class<Float> clazz) {
        return DEFAULT_FLOAT;
    }
}
