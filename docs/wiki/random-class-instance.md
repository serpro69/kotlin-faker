---
icon: material/ice-cream
---

# :material-ice-cream: Random instance of any class {#random-class-instance}

It is possible to create a random instance of (almost) any class. 

There are some rules to keep in mind:

- A class must have a public or internal constructor
- By default, a constructor with the least number of arguments is used (This can be configured - read on.)
- `kolin.Array` type in the constructor is not supported at the moment
- Inner classes (either direct generation or as class parameter type) are not supported at the moment

Random instance generation is available through `Faker().randomProvider`:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_one"
    ```

### Random Class Instance Configuration

Random Class Instance configuration can be applied on several levels. Consider the following classes:


=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_ten"
    ```

#### Configuration via `FakerConfig`

This takes the least precedence and applies to all instances (see [Making a copy/new instance of RandomClassProvider](#making-a-new-instance-of-random-class-provider)) of `RandomClassProvider` if set.

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_eleven"
    ```

#### Configuration via `Faker#randomProvider`

This takes higher precedence and will also merge any configuration that was set on the previous level.

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_twelve"
    ```

#### Configuration via `randomClassInstance` function

This configuration takes the most precedence and does not take into account configurations applied on other levels.

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_thirteen"
    ```

### Pre-Configuring type generation

#### Predefined types for constructor parameters

Some, or all, of the constructor params can be instantiated with values following some pre-configured logic using `typeGenerator` or `namedParameterGenerator` functions. Consider the following example:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_two"
    ```

So for each instance of `Baz` the following will be true:

```kotlin
baz.id == 0
baz.user == "user_X3a8s813dcb"
baz.uuid == UUID.fromString("00000000-0000-0000-0000-000000000000")
baz.relatedUuid == UUID.fromString("11111111-1111-1111-1111-111111111111")
```

This example itself does not make that much sense, since we're using "static" values, but we could also do something like:

=== "kotlin"
    ```kotlin
    val baz: Baz = faker.randomProvider.randomClassInstance {
        typeGenerator<UUID> { UUID.randomUUID() }
    }
    ```

...or even so:

=== "kotlin"
    ```kotlin
    class Person(val id: Int, val name: String)

    val person: Person = faker.randomProvider.randomClassInstance {
        typeGenerator<String> { faker.name.name() }
    }
    ```

#### Pre-defined instance for classes with no public or internal constructors

By default, `randomClassInstance` can't generate classes with no public constructors, but this can be worked around by using `typeGenerator`:

=== "kotlin"
    ```kotlin
    faker.randomProvider.randomClassInstance<Instant> {
        typeGenerator<Instant> { Instant.now() }
    }
    ```

You could also use the same approach to create interfaces, for example:

=== "kotlin"
    ```kotlin
    interface TestInterface {
      val id: Int
      val name: String
    }

    val testInterface = randomProvider.randomClassInstance<TestInterface> {
      typeGenerator<TestInterface> {
        object : TestInterface {
          override val id: Int = 42
          override val name: String = "Deep Thought"
        }
      }
    }

    testInterface.id shouldBe 42
    testInterface.name shouldBe "Deep Thought"
    ```

A random class instance will be generated using the following precedence rules:

- object instance
- "default instance" of a class
  - uses a public/internal constructor with the least number of arguments, unless otherwise configured (see [Random Class Instance Configuration](#random-class-instance-configuration))
- "predefined instance" of a class if no public or internal constructors are found
- failing all of the above, `NoSuchElementException` will be thrown

#### Predefined collection element types

It may be desirable to define how elements of a `Collection` (currently supports `List`s and `Set`s) constructor parameter type are generated, for this `collectionElementTypeGenerator` function can be used:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_sixteen"
    ```

So for each instance of `Baz` the following will be true:

```kotlin
baz.list.all { it == "list" }
baz.set.all {it == "string" }
```

This example kind of makes little sense, since we're using "static" values, so it's just for example purposes.

!!! info
    Note how we can use `it.type.classifier` to figure out the parameter classifier and further customize generation for different collection types.
    <br>
    Explore other properties of `it`, which exposes more details about the [parameter](https://serpro69.github.io/kotlin-faker/kotlindoc/core-api/io/github/serpro69/kfaker/provider/misc/parameterinfo/) that is being customized.

There are also two similar methods for map entries: `mapEntryKeyTypeGenerator` and `mapEntryValueTypeGenerator`, which can be used to configure how keys and values are generated in `Map` constructor parameter types:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_seventeen"
    ```

Nullable collection element types are also supported (but note that `null`s as values are never returned):

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_eighteen"
    ```

### Deterministic constructor selection

By default, the constructor with the least number of args is used when creating a random instance of the class. This might not always be desirable and can be configured. Consider the following example:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_three"
    ```

If there is a need to use the constructor with 3 arguments when creating an instance of `FooBarBaz`, we can do it like so:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_four"
    ```

In the above example, `FooBarBaz` will be instantiated with the first discovered constructor that has `parameters.size == 3`; if there are multiple constructors that satisfy this condition - a random one will be used. Failing that (for example, if there is no such constructor), a constructor with the maximum number of arguments will be used to create an instance of the class.

Alternatively to `constructorParamSize`, a `constructorFilterStrategy` config property can be used as well:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_five"
    ```

The above has the following rules:

- `constructorParamSize` config property takes precedence over `constructorFilterStrategy`
- both can be specified at the same time, though in most cases it probably makes more sense to use `fallbackStrategy` with `constructorParamSize` as it just makes things a bit more readable
- configuration properties that are set in `randomClassInstance` block will be applied to all "children" classes. For example classes `Foo`, `Bar`, and `Baz` will use the same random instance configuration settings when instances of those classes are created in `FooBarBaz` class.

### Default values selection

By default, all parameters of a [selected constructor](#deterministic-constructor-selection) would be generated with randomized values:

=== "kotlin"

    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_nineteen"
    --8<-- "Extras.kt:extras_random_instance_twenty"
    ```

This behavior can be changed with `defaultValuesStrategy` configuration option.

One can choose between generating values only for non-optional constructor parameters:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_twenty_one"
    ```

...or randomly selecting between a default value and a randomly-generated one:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_twenty_two"
    ```

### Configuring the size of generated Collections

Support for `kotlin.collections.Collection` parameter types - `List`, `Set` and `Map` has been added in version `1.9.0` and with that - a new configuration parameter to configure the size of the generated collection.

By default, all collections will be generated with only 1 element:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_six"
    ```

This can be configured using `collectionsSize` parameter:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_seven"
    ```

!!! info
    Note that the `collectionsSize` configuration parameter affects all 3 types of Collections.

!!! warning
    It is also worth noting that `typeGenerator<Foo> { ... }` configuration, which was covered above, will not affect `Foo` typed elements in a generated collection.

Consider the following example. If `typeGenerator<String> { "a string" }` would affect `String` typed elements of `Set`, the resulting generated set would be of size `1`:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_eight"
    ```

At the same time, `typeGenerator` configurator itself can be used with collections as well:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_nine"
    ```

### Making a new instance of Random Class Provider

`RandomClassProvider` has two functions: `new` and `copy`, that allow you to create another instance of the class, for example, a one that has a different type generation configuration.

#### New Instance

To make a new instance of `randomProvider`:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_fourteen"
    ```

!!! info
    Any configuration set via `fakerConfig` ( ❶ ), will be applied to the `new` instance ( ❸ ) as well.
    <br>
    Any configuration set via `faker.randomProvider` instance ( ❷ ) is NOT applied to the `new` instance.

#### Instance Copy

To make a copy of an existing instance of `randomProvider`:

=== "kotlin"
    ```kotlin
    --8<-- "Extras.kt:extras_random_instance_fifteen"
    ```

!!! info
    Any configuration that was already applied to `faker.randomProvider` ( ❶ and ❷ ), will be applied to the `copy` ( ❸ ) as well.
    <br>
    The `copy`, just as `new` instance, can of course be reconfigured ( ❹ ) as needed, which does not affect the configuration of the `faker.randomProvider` or configurations of other "copies".

### Dealing with Generic Types

Generic parameter types are not fully supported at this moment due to type-erasure on the JVM (See also https://github.com/serpro69/kotlin-faker/issues/191)

### Top-level functions

Sometimes you just want to generate random POJO instances and don't need the whole range of kotlin-faker functionality?
For these cases you can import `randomClassInstance` top-level function and use it directly w/o the need to instantiate an instance of `Faker`

=== "kotlin"
    ```kotlin
    import io.github.serpro69.kfaker.randomClassInstance

    val randomClass = randomClassInstance<MyClass>()
    ```

It can also be configured via `configurator` lambda parameter, or via `FakerConfig` instance:

=== "kotlin"
    ```kotlin
    import io.github.serpro69.kfaker.fakerConfig
    import io.github.serpro69.kfaker.randomClassInstance
    import java.time.Instant

    val randomClass = randomClassInstance<MyClass>() {
      typeGenerator<Instant> { Instant.now() }
    }

    val cfg = fakerConfig {
      randomClassInstance {
        typeGenerator<Instant> { Instant.MIN }
      }
    }
    val another = randomClassInstance<MyClass>(cfg)
    ```

