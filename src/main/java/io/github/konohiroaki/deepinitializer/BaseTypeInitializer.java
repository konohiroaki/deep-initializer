package io.github.konohiroaki.deepinitializer;

/**
 * Base class used by all type initializers.
 */
public abstract class BaseTypeInitializer<T> {

    /**
     * Initializes a class with type T
     */
    public abstract T init(Class<T> clazz);
}
