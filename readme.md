# Deep Initializer
[![Build Status](https://travis-ci.org/konohiroaki/deep-initializer.svg?branch=master)](https://travis-ci.org/konohiroaki/deep-initializer)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.konohiroaki/deep-initializer/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.konohiroaki/deep-initializer)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)](https://opensource.org/licenses/mit-license.php)

Initialize deep bean recursively filling with default values.

```java
======= A.java
class A {
    int num;
}

======= B.java
class B extends A {
    @ApiModelProperty(example = "true")
    boolean bool;
    C c;
}

======= C.java
class C {
    @ApiModelProperty(example = "Hello World!")
    String str;
}

======= SomeTest.java
@Test
public void test() {
    B b = new DeepInitializer().init(B.class);

    assertThat(b.num, is(0));
    assertThat(b.bool, is(true));
    assertThat(b.c, is(not(nullValue())));
    assertThat(b.c.str, is("Hello World!"));
}
```

## When do you want to use it?
This might be helpful when you are testing your RESTful APIs.

Creating requests for those APIs by manually writing Java code like this

```java
SomeApiRequest rq = new SomeApiRequest();
rq.setA(true);
rq.setB(new B());
rq.getB().setC(false);
...more and more sets
```

is so much waste of time. Rather than doing this, let's do this.

```
SomeApiRequest rq = new DeepInitializer().init(SomeApiRequest.class);
```

You can instantly build a deep bean filled with default values defined by `@ApiModelProperty#example(String)`.

`@ApiModelProperty` is an annotation provided by Swagger project.

## Specification

### Default initializer

#### Type Initializer
| Type | Value |
|---|---|
| Primitive Types | Its default value defined in JLS |
| Primitive Wrapper Types | Refers its primitive type's default value |
| `String` | `""` |
| `Enum`| `EnumType.values()[0]`|
| `List` or its derived type | `new ArrayList<>()` |
| `Set` or its derived type | `new HashSet<>()` |
| `Map` or its derived type | `new HashMap<>()` |
| Others | `new OtherType()` and fills its fields with the field default initializer |

#### Field Initializer
| Type | Value set when annotation absent | Value set when `example` is set |
|---|---|---|
| Primitive Types | Refers type initializer | `Type.valueOf(example)` |
| Primitive Wrapper Types | Refers type initializer | `Type.valueOf(example)` |
| `String` | Refers type initializer | `example`|
| `Enum`| Refers type initializer| Value with same `name()` |

It fallbacks to type initializer when there is no field initializer.

### Custom initializer
You can add/remove your custom initializer by implementing it.

Extend `BaseTypeInitializer<>` or `BaseFieldInitializer<>` for your custom initializer.

```
class C {
    @ApiModelProperty(example = "Hello World!")
    String str;
}

class CustomIntegerInit extends BaseTypeInitializer<Integer> {
    @Override public Integer init(Class<Integer> clazz) { return 10; }
}

public static void main(String[] args) {
    DeepInitializer deep = new DeepInitializer();
    deep.addTypeInitializer(Integer.class, new CustomIntegerInit());
    Integer num = deep.init(Integer.class);
    // ==> 10
    // Added initializer takes priority than the default. (Specifically, later added has higher priority)

    deep.removeFieldInitializer(String.class);
    C str = deep.init(C.class);
    // ==> ""
    // Field initializer for String.class is removed but there is no custom field initializer for String.class added.
    // In this case, it fallbacks to type initializer.
    // We haven't added any custom type initializer for String.class thus it will use the default one.
}
```