package io.github.konohiroaki.deepinitializer.swagger;

import org.junit.Test;

import java.lang.reflect.Field;

import io.swagger.annotations.ApiModelProperty;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SwaggerBooleanInitializerTest {

    @ApiModelProperty(example = "true")
    private boolean example;

    @Test
    public void test() throws Exception {
        SwaggerBooleanInitializer init = new SwaggerBooleanInitializer();
        Field field = SwaggerBooleanInitializerTest.class.getDeclaredField("example");
        Boolean actual = init.init(field);

        assertThat(actual, is(true));
    }
}
