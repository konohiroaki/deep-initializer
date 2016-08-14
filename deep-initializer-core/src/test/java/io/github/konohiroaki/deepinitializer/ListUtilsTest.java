package io.github.konohiroaki.deepinitializer;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

public class ListUtilsTest {

    @Test
    public void reverse() throws Exception {
        List<String> list = Arrays.asList("hello", "world", "thank", "you");

        List<String> actual = ListUtils.reverse(list);

        assertThat(list, contains("hello", "world", "thank", "you"));
        assertThat(actual, contains("you", "thank", "world", "hello"));
    }
}