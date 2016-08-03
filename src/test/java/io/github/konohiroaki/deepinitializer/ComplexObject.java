package io.github.konohiroaki.deepinitializer;

import java.nio.file.AccessMode;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ComplexObject extends SimpleObject {

    private int intA;
    private long longA;
    private double doubleA;
    private String stringA;
    private AccessMode accessModeEnum;
    private List<Integer> integerList;
    private Set<AccessMode> accessModeSet;
    private Map<String, Integer> integerMap;
    private Map<String, List<AccessMode>> accessModeMap;
    private ComplexObject2 complexObject2;
}
