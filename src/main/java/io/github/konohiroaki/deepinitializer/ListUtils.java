package io.github.konohiroaki.deepinitializer;

import java.util.ArrayList;
import java.util.List;

class ListUtils {

    static <T> List<T> reverse(List<T> list) {
        List<T> reversed = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reversed.add(list.get(i));
        }
        return reversed;
    }
}
