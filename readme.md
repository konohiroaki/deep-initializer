# Deep Initializer

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
    B b = DeepInitializer.initialize(B.class);

    assertThat(b.num, is(0));
    assertThat(b.bool, is(true));
    assertThat(b.c, is(not(nullValue())));
    assertThat(b.c.str, is("Hello World!"));
}
```

## When do you want to use it?
This might be helpful when you are testing your RESTful APIs. Creating requests for those APIs by manually writing Java code like this

```java
SomeApiRequest rq = new SomeApiRequest();
rq.setA(true);
rq.setB(new B());
rq.getB().setC(false);
...more and more set
```

for each APIs are so much waste of time. Rather than doing this, let's do this.

```
SomeApiRequest rq = DeepInitializer.initialize(SomeApiRequest.class);
```

You can instantly build a request object filled with default values defined by `@io.swagger.annotations.ApiModelProperty#example(String)`.

## Specification
| Type | Value set when no annotation | Value when `example` is set |
|---|---|---|
| Primitive Types | Its default value defined in JLS | `Type.valueOf(example)` |
| Primitive Wrapper Types | Refers its primitive type's default value | `Type.valueOf(example)` |
| `String` | "" | `example`|
| `Enum`| `EnumType.values()[0]`| Does not affect |
| `List` or (extends\|implements) `List` | `new ArrayList<>()` | Does not affect |
| `Set` or (extends\|implements) `Set` | `new HashSet<>()` | Does not affect |
| `Map` or (extends\|implements) `Map` | `new HashMap<>()` | Does not affect |
| Others | `new OtherType()` and fills its fields following the same rule | Does not affect |
