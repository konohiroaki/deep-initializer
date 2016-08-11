# Deep Initializer
## [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.konohiroaki/deep-initializer/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.konohiroaki/deep-initializer) [![license](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)](https://opensource.org/licenses/mit-license.php)

## [![Build Status](https://travis-ci.org/konohiroaki/deep-initializer.svg?branch=master)](https://travis-ci.org/konohiroaki/deep-initializer) [![Coverage Status](https://coveralls.io/repos/github/konohiroaki/deep-initializer/badge.svg?branch=master)](https://coveralls.io/github/konohiroaki/deep-initializer)

Initialize deep bean recursively with customizable initializers.

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

======= Main.java
public static void main(String[] args) {
    B b = new DeepInitializer().init(B.class);

    System.out.println(b.num); // ==> 0
    System.out.println(b.bool); // ==> true
    System.out.println(b.c.str); // ==> "Hello World!"
}
```

## When do you want to use it?
This might be helpful when you want to test an API which requires deep bean for calling it.

Creating deep beans for those APIs by manually writing Java code like this

```java
SomeApiRequest rq = new SomeApiRequest();
rq.setA(true);
rq.setB(new B());
rq.getB().setC(10);
rq.getB().setD("Hello");
...more and more sets
```

is so much waste of time. Rather than doing that, let's do this.

```java
SomeApiRequest rq = new DeepInitializer().init(SomeApiRequest.class);
```

You can instantly build a deep bean. The way how it initializes depends on the initializers you use. Yes, you can create your custom initializer for specific types!

By default, some types uses `@ApiModelProperty#example(String)` to specify a default value like above.

`@ApiModelProperty` is an annotation provided by Swagger project.

## Specification

There are two kinds of initalizers, "type initializer" and "field initializer". Type initializer only refers its type information to decide its value. Field initializer refers the field information thus you can get some more information than type initializer. So these two are not that different, and if there's no field initializer for a type, it fallbacks to its type initializer.

The parameter you pass to `DeepInitializer#init(Class)` is just a type thus it cannot refer any field information. But the fields declared in that type can refer its field information.

### Default initializer

Default initializers are set when you `new DeepInitializer()`. You can remove default initializers if you don't want to use it.

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
| Type | Value when annotation absent | Value when `example` is set |
|---|---|---|
| Primitive Types | Refers type initializer | `Type.valueOf(example)` |
| Primitive Wrapper Types | Refers type initializer | `Type.valueOf(example)` |
| `String` | Refers type initializer | `example`|
| `Enum`| Refers type initializer| Value with same `name()` |

(`example` comes from `@ApiModelProperty` annotation.)

### Custom initializer

The initialization behaviour is fully customizable. You can add your custom initializers to change the behaviour of each initialization. To do so, extend `BaseTypeInitializer<>` or `BaseFieldInitializer<>`.

```java
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
    System.out.println(deep.init(Integer.class)); // ==> 10
    // Custom initializer takes priority than the default. (Specifically, later added has higher priority)

    deep.removeFieldInitializer(String.class);
    System.out.println(deep.init(C.class).str); // ==> ""
    // Field initializer for String.class is removed
    // and there's no custom field initializer for String.class set after that.
    // In this case, it fallbacks to type initializer.
    // You haven't added any custom type initializer for String.class thus it'll use the default one.
}
```

## Contribute
For any bug reports, enhancement requests, or code patches, please use the [GitHub Issue system](https://github.com/konohiroaki/deep-initializer/issues).