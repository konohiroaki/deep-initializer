package io.github.konohiroaki.deepinitializer;

import java.nio.file.AccessMode;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.swagger.annotations.ApiModelProperty;

class ComplexObject extends SimpleObject {

    @ApiModelProperty(example = "5")
    private int intA;
    private long longA;
    private double doubleA;
    @ApiModelProperty(example = "exampleString")
    private String stringA;
    private AccessMode accessModeEnum;
    private List<Integer> integerList;
    private Set<ComplexObject2> accessModeSet;
    private Map<String, Integer> integerMap;
    private Map<String, List<AccessMode>> accessModeMap;
    private ComplexObject2 complexObject2;
}
