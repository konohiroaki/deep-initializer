# Deep Initializer
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.konohiroaki/deep-initializer-core/badge.svg)](http://search.maven.org/#search|ga|1|g:io.github.konohiroaki AND a:deep-initializer-*!parent)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)](https://opensource.org/licenses/mit-license.php)

[![Build Status](https://travis-ci.org/konohiroaki/deep-initializer.svg?branch=master)](https://travis-ci.org/konohiroaki/deep-initializer)
[![Coverage Status](https://img.shields.io/codecov/c/github/konohiroaki/deep-initializer/master.svg)](https://codecov.io/gh/konohiroaki/deep-initializer/branch/master)

Initialize deep bean recursively with customizable initializers.

```java
======= A.java
class A {
    int num;
}

======= B.java
class B extends A {
    boolean bool;
    C c;
}

======= C.java
class C {
    String str;
}

======= Main.java
public static void main(String[] args) {
    B b = new DeepInitializer().init(B.class);

    System.out.println(b.num); // ==> 0
    System.out.println(b.bool); // ==> false
    System.out.println(b.c.str); // ==> ""
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

You can instantly build a deep bean. The way how it initializes a bean depends on the initializers you use. Yes, you can create your custom initializer for specific types!

## Specification

There are two kinds of initalizers, "type initializer" and "field initializer". Type initializer only refers its type information to decide its value. Field initializer refers the field information thus you can get some more information than type initializer. So these two are not that different, and if there's no field initializer for a type, it fallbacks to its type initializer.

The parameter you pass to `DeepInitializer#init(Class)` is just a type thus it cannot refer any field information. But the fields declared in that type can refer its field information.

### Default initializer

Default initializers are set when you `new DeepInitializer()`. You cannot remove default initializers but you can add your custom initializers and that will be prioritized than the default ones.

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

There is no default field initializers. It only refers type information by default.

### Custom initializer

The initialization behaviour is fully customizable. You can add your custom initializers to change the behaviour of each initialization. To do so, extend `BaseTypeInitializer<>` or `BaseFieldInitializer<>`.

```java
class ExamplePojo {
    @CustomAnnotation("Hello World!")
    String str1;
    String str2;
}

@Retention(RetentionPolicy.RUNTIME)
@interface CustomAnnotation {
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
    System.out.println(deep.init(String.class));           // ==> "DEFAULT"
    // Custom initializer takes priority than the default. (Specifically, later added has higher priority)

    deep.addFieldInitializer(String.class, new CustomStringFieldInitializer());
    System.out.println(deep.init(ExamplePojo.class).str1); // ==> "Hello World!"
    System.out.println(deep.init(ExamplePojo.class).str2); // ==> "DEFAULT"

    deep.removeFieldInitializer(String.class);
    System.out.println(deep.init(ExamplePojo.class).str1); // ==> "DEFAULT"
    System.out.println(deep.init(ExamplePojo.class).str2); // ==> "DEFAULT"

    deep.removeTypeInitializer(String.class);
    // You cannot remove Default initializers.
    System.out.println(deep.init(ExamplePojo.class).str1); // ==> ""
    System.out.println(deep.init(ExamplePojo.class).str2); // ==> ""
}
```

## Contribute
For any bug reports, enhancement requests, or code patches, please use the [GitHub Issue system](https://github.com/konohiroaki/deep-initializer/issues).