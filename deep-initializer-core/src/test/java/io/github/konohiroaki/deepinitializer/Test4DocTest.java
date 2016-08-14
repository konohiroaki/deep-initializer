package io.github.konohiroaki.deepinitializer;

import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

public class Test4DocTest {

    @Retention(RetentionPolicy.RUNTIME) @interface CustomAnnotation {

        String value();
    }

    class CustomStringTypeInitializer extends BaseTypeInitializer<String> {

        @Override public String init(Class<String> clazz) {
            return "DEFAULT";
        }
    }

    class CustomStringFieldInitializer extends BaseFieldInitializer<String> {

        @Override public String init(Field field) {
            CustomAnnotation annotation = field.getAnnotation(CustomAnnotation.class);
            if (annotation != null && annotation.value() != null) {
                return annotation.value();
            }
            return new CustomStringTypeInitializer().init(String.class);
        }
    }

    @Test
    public void test() {
        DeepInitializer deep = new DeepInitializer();

        deep.addTypeInitializer(String.class, new CustomStringTypeInitializer());
        System.out.println("1 " + deep.init(String.class));           // ==> "DEFAULT"
        // Custom initializer takes priority than the default. (Specifically, later added has higher priority)

        deep.addFieldInitializer(String.class, new CustomStringFieldInitializer());
        System.out.println("2 " + deep.init(ExamplePojo.class).str1); // ==> "Hello World!"
        System.out.println("3 " + deep.init(ExamplePojo.class).str2); // ==> "DEFAULT"

        deep.removeFieldInitializer(String.class);
        System.out.println("4 " + deep.init(ExamplePojo.class).str1); // ==> "DEFAULT"
        System.out.println("5 " + deep.init(ExamplePojo.class).str2); // ==> "DEFAULT"

        deep.removeTypeInitializer(String.class);
        // You cannot remove Default initializers.
        System.out.println("6 " + deep.init(ExamplePojo.class).str1); // ==> ""
        System.out.println("7 " + deep.init(ExamplePojo.class).str2); // ==> ""
    }
}
