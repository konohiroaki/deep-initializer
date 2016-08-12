package io.github.konohiroaki.deepinitializer;

public class DoubleTypeInitializer extends BaseTypeInitializer<Double> {

    private static double DEFAULT_DOUBLE;

    @Override
    public Double init(Class<Double> clazz) {
        return DEFAULT_DOUBLE;
    }
}
