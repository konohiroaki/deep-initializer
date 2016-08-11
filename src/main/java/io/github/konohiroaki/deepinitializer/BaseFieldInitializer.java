package io.github.konohiroaki.deepinitializer;

import java.lang.reflect.Field;

/**
 * Base class used by all field initializers.
 */
public abstract class BaseFieldInitializer<T> {

    /**
     * Initializes a field with type T
     */
    public abstract T init(Field field);
}
