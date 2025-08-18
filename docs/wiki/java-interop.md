---
icon: fontawesome/brands/java
---

# :fontawesome-brands-java: Java Interop

## Using Faker DSL

Even though **kotlin-faker** was created with Kotlin in mind, it is still possible to use this library from a Java-based project thanks to great
Kotlin-to-Java interop. 

!!! info
    There are similar "faker" libraries written in Java for Java, so you might consider those as well. Check out the [JVM-targeted Faker Libs Comparison](../pages/faker-comparisons.md) for some comparison details with other faker libs if you're interested.

Kotlin faker comes with a DSL-like functions (see also [Faker DSL](./faker-dsl.md)), and while this works great with Kotlin with its type inferences and implicit returns from lambdas, "functional Kotlin-to-Java" interoperability is far from nice.

To somehow mitigate these limitations, kotlin faker comes with a `FunctionalUtils` java class that has a static `fromConsumer` method which takes a function and returns the last statement, thus avoiding the need to specify the `return` explicitly.

Consider the following example of creating and configuring a `Faker` instance with the DSL:

```java
Faker faker = faker(fromConsumer(f -> {
    f.config(fromConsumer(config -> {
        config.setRandomSeed(42L);
    }));
}));
```

This of course doesn't look as good as Kotlin, but that's Java for you with its ugly lambdas.

However, if `builder` parameters are not called with the help of `fromConsumer` method, then explicit returns should be specified:

```java
Faker faker = faker(f -> {
    f.config(config -> {
        config.setRandomSeed(42L);
        return Unit.INSTANCE;
    });
    return Unit.INSTANCE;
});
```

Fewer parentheses, but an explicit `return` statement for each lambda - the choice, as they say, is yours.

## Calling `Faker` methods

Calling a faker method is pretty straightforward. The only difference between kotlin and java here is that in java you need to call the getter of the fake data provider properties such as `name`, `address`, and so on.

=== "java"
    ```java
    faker.getName().firstName()
    faker.getAddress().city()
    ```

=== "kotlin"
    ```kotlin
    faker.name.firstName()
    faker.address.city()
    ```
