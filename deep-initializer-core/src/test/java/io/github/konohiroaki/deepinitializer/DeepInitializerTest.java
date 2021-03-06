package io.github.konohiroaki.deepinitializer;

import org.junit.Test;

import java.nio.file.AccessMode;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class DeepInitializerTest {

    @Test
    public void test() throws Exception {
        ComplexObject complexObject = new DeepInitializer().init(ComplexObject.class);
    }

    @Test
    public void test2() throws Exception {
        int integer = new DeepInitializer().init(int.class);
    }

    @Test
    public void test3() throws Exception {
        Integer integer = new DeepInitializer().init(Integer.class);
    }

    @Test
    public void test4() throws Exception {
        String str = new DeepInitializer().init(String.class);
    }

    @Test
    public void test5() throws Exception {
        List<String> str = new DeepInitializer().init(String.class, 3);
    }

    @Test
    public void test10() throws Exception {
        Set<String> str = new DeepInitializer().init(Set.class);
    }

    @Test
    public void testEnumWithDefault() {
        AccessMode mode = new DeepInitializer().init(AccessMode.class);
        assertThat(mode, is(AccessMode.READ));
    }

    @Test
    public void testEnumWithCustom() {
        DeepInitializer deep = new DeepInitializer();
        deep.addTypeInitializer(Enum.class, new CustomEnumInit());
        AccessMode mode = deep.init(AccessMode.class);
        assertThat(mode, is(AccessMode.WRITE));

    }

    @Test
    public void testEnumWithoutFieldInitializer() {
        DeepInitializer deep = new DeepInitializer();
        deep.removeFieldInitializer(Enum.class);
        ComplexObject2 obj = deep.init(ComplexObject2.class);
        assertThat(obj.accessMode, is(AccessMode.READ));
    }

    @Test
    public void testSpecificEnumCustomInitializer() {
        DeepInitializer deep = new DeepInitializer();
        deep.addTypeInitializer(Integer.class, new CustomIntegerInit());
        Integer mode = deep.init(Integer.class);
        assertThat(mode, is(10));
    }

    private class CustomEnumInit extends BaseTypeInitializer<Enum> {

        @Override public Enum<?> init(Class<Enum> clazz) {
            return clazz.getEnumConstants()[1];
        }
    }

    private class CustomIntegerInit extends BaseTypeInitializer<Integer> {

        @Override public Integer init(Class<Integer> clazz) {
            return 10;
        }
    }
}
