package io.github.konohiroaki.deepinitializer;

import org.junit.Test;

import java.util.List;

public class DeepInitializerTest {

    @Test
    public void test() throws Exception {
        ComplexObject complexObject = DeepInitializer.initialize(ComplexObject.class);
    }

    @Test
    public void test2() throws Exception {
        int integer = DeepInitializer.initialize(int.class);
    }

    @Test
    public void test3() throws Exception {
        Integer integer = DeepInitializer.initialize(Integer.class);
    }

    @Test
    public void test4() throws Exception {
        String str = DeepInitializer.initialize(String.class);
    }

    @Test
    public void test5() throws Exception {
        List<String> str = DeepInitializer.initialize(String.class, 3);
    }
}
