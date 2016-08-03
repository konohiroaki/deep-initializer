package io.github.konohiroaki.deepinitializer;

import org.junit.Test;

public class DeepInitializerTest {

    @Test
    public void test() throws Exception {
        ComplexObject complexObject = DeepInitializer.initialize(ComplexObject.class);
    }
}
