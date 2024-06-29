---
---

# Extras

`kotlin-faker` provides additional functionality outside of data generation from static .yml dictionaries.

## ToC

* [Random instance of any class](#random-instance-of-any-class)
  * [Random Class Instance Configuration](#random-class-instance-configuration)
  * [Pre-configuring type generation](#pre-configuring-type-generation)
    * [Predefined types for constructor parameters](#predefined-types-for-constructor-parameters)
    * [Pre-defined instance for classes with no public constructors](#pre-defined-instance-for-classes-with-no-public-constructors)
    * [Predefined collection element types](#predefined-collection-element-types)
  * [Deterministic constructor selection](#deterministic-constructor-selection)
  * [Configuring the size of generated Collections](#configuring-the-size-of-generated-collections)
  * [Making a Copy or a New instance of RandomClassProvider](#making-a-new-instance-of-random-class-provider)
    * [New Instance](#new-instance)
    * [Instance Copy](#instance-copy)
  * [Dealing with Generic Types](#dealing-with-generic-types)
* [Random Everything](#random-everything)
* [Random Strings from Templates](#random-strings-from-templates)

<br>

## Random instance of any class

It is possible to create a random instance of (almost) any class. 

There are some rules to keep in mind:

- By default, the constructor with the least number of arguments is used (This can be configured - read on.)
- `kolin.Array` type in the constructor is not supported at the moment
- Inner classes (either direct generation or as class parameter type) are not supported at the moment

Random instance generation is available through `Faker().randomProvider`:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_one' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

{% btc %}{% endbtc %}

<br>

### Random Class Instance Configuration

Random Class Instance configuration can be applied on several levels. Consider the following classes:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_ten' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

<br>

#### Configuration via `FakerConfig`

This takes the least precedence and applies to all instances (see [Making a copy/new instance of RandomClassProvider]({{ link(collectionType='wiki', collectionId='', itemId='Extras') }}#making-a-new-instance-of-random-class-provider)) of `RandomClassProvider` if set.

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_eleven' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

<br>

#### Configuration via `Faker#randomProvider`

This takes higher precedence and will also merge any configuration that was set on the previous level.

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_twelve' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

<br>

#### Configuration via `randomClassInstance` function

This configuration takes the most precedence and does not take into account configurations applied on other levels.

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_thirteen' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

{% btc %}{% endbtc %}

<br>

### Pre-Configuring type generation

#### Predefined types for constructor parameters

Some, or all, of the constructor params can be instantiated with values following some pre-configured logic using `typeGenerator` or `namedParameterGenerator` functions. Consider the following example:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_two' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

<br>
So for each instance of `Baz` the following will be true:

```kotlin
baz.id == 0
baz.user == "user_X3a8s813dcb"
baz.uuid == UUID.fromString("00000000-0000-0000-0000-000000000000")
baz.relatedUuid == UUID.fromString("11111111-1111-1111-1111-111111111111")
```

This example itself does not make that much sense, since we're using "static" values, but we could also do something like:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
val baz: Baz = faker.randomProvider.randomClassInstance {
    typeGenerator<UUID> { UUID.randomUUID() }
}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

<br>
...or even so:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
class Person(val id: Int, val name: String)

val person: Person = faker.randomProvider.randomClassInstance {
    typeGenerator<String> { faker.name.name() }
}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

<br>

#### Pre-defined instance for classes with no public constructors

By default, `randomClassInstance` can't generate classes with no public constructors, but this can be worked around by using `typeGenerator`:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
faker.randomProvider.randomClassInstance<Instant> {
    typeGenerator<Instant> { Instant.now() }
}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

You could also use the same approach to create interfaces, for example:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
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
{% endfilter %}
{% endkotlin %}

{% endtabs %}

A random class instance will be generated using the following precedence rules:

- object instance
- "default instance" of a class
  - uses the public constructor with the least number of arguments, unless otherwise configured (see [Random Class Instance Configuration]({{ link(collectionType='wiki', collectionId='', itemId='Extras') }}#random-class-instance-configuration))
- "predefined instance" of a class if no public constructors are found
- failing all of the above, `NoSuchElementException` will be thrown

{% btc %}{% endbtc %}

<br>

#### Predefined collection element types

It may be desirable to define how elements in a collection parameter are generated, for this `collectionElementTypeGenerator` function can be used:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_sixteen' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

<br>

So for each instance of `Baz` the following will be true:

```kotlin
baz.list.all { it == "list" }
baz.set.all {it == "string" }
```

This example kind of makes little sense, since we're using "static" values, so it's just for example purposes.

{% info %}
{% filter compileAs('md') %}
Note how we can use `it.type.classifier` to figure out the parameter classifier and further customize generation for different collection types.

Explore other properties of `it`, which exposes more details about the [parameter](https://serpro69.github.io/kotlin-faker/kotlindoc/core-api/io/github/serpro69/kfaker/provider/misc/parameterinfo/) that is being customized.
{% endfilter %}
{% endinfo %}

<br>

### Deterministic constructor selection

By default, the constructor with the least number of args is used when creating a random instance of the class. This might not always be desirable and can be configured. Consider the following example:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_three' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

<br>
If there is a need to use the constructor with 3 arguments when creating an instance of `FooBarBaz`, we can do it like so:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_four' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

<br>
In the above example, `FooBarBaz` will be instantiated with the first discovered constructor that has `parameters.size == 3`; if there are multiple constructors that satisfy this condition - a random one will be used. Failing that (for example, if there is no such constructor), a constructor with the maximum number of arguments will be used to create an instance of the class.

Alternatively to `constructorParamSize`, a `constructorFilterStrategy` config property can be used as well:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_five' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

The above has the following rules:

- `constructorParamSize` config property takes precedence over `constructorFilterStrategy`
- both can be specified at the same time, though in most cases it probably makes more sense to use `fallbackStrategy` with `constructorParamSize` as it just makes things a bit more readable
- configuration properties that are set in `randomClassInstance` block will be applied to all "children" classes. For example classes `Foo`, `Bar`, and `Baz` will use the same random instance configuration settings when instances of those classes are created in `FooBarBaz` class.

{% btc %}{% endbtc %}

<br>

### Configuring the size of generated Collections

Support for `kotlin.collections.Collection` parameter types - `List`, `Set` and `Map` has been added in version `1.9.0` and with that - a new configuration parameter to configure the size of the generated collection.

By default, all collections will be generated with only 1 element:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_six' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

<br>

This can be configured using `collectionsSize` parameter:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_seven' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

{% info %}
{% filter compileAs('md') %}
Note that the `collectionsSize` configuration parameter affects all 3 types of Collections.
{% endfilter %}
{% endinfo %}

<br>

{% warn %}
{% filter compileAs('md') %}
It is also worth noting that `typeGenerator<Foo> { ... }` configuration, which was covered above, will not affect `Foo` typed elements in a generated collection.
{% endfilter %}
{% endwarn %}

Consider the following example. If `typeGenerator<String> { "a string" }` would affect `String` typed elements of `Set`, the resulting generated set would be of size `1`:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_eight' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

<br>

At the same time, `typeGenerator` configurator itself can be used with collections as well:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_nine' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

{% btc %}{% endbtc %}

<br>

### Making a new instance of Random Class Provider

`RandomClassProvider` has two functions: `new` and `copy`, that allow you to create another instance of the class, for example, a one that has a different type generation configuration.

#### New Instance

To make a new instance of `randomProvider`:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_fourteen' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}


<br>

{% info %}
{% filter compileAs('md') %}
Any configuration set via `fakerConfig` ( ❶ ), will be applied to the `new` instance ( ❸ ) as well.
Any configuration set via `faker.randomProvider` instance ( ❷ ) is NOT applied to the `new` instance.
{% endfilter %}
{% endinfo %}

<br>

#### Instance Copy

To make a copy of an existing instance of `randomProvider`:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_instance_fifteen' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

<br>

{% info %}
{% filter compileAs('md') %}
Any configuration that was already applied to `faker.randomProvider` ( ❶ and ❷ ), will be applied to the `copy` ( ❸ ) as well.

The `copy`, just as `new` instance, can of course be reconfigured ( ❹ ) as needed, which does not affect the configuration of the `faker.randomProvider` or configurations of other "copies".
{% endfilter %}
{% endinfo %}

{% btc %}{% endbtc %}

<br>

### Dealing with Generic Types

Generic parameter types are not fully supported at this moment due to type-erasure on the JVM (See also https://github.com/serpro69/kotlin-faker/issues/191)

{% btc %}{% endbtc %}

<br>

## Random Everything

Faker provides its wrapper functions around `java.util.Random` (with some additional functionality that is not covered by `java.util.Random`) through `Faker().random` property.

### Wrappers around `java.util.Random`

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_everything_one' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

### Random Enum Instance

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_everything_two' %}

{% snippet 'extras_random_everything_three' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

### Random Strings

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_everything_four' %}

{% snippet 'extras_random_everything_five' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

### Random sub-lists and sub-sets

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_everything_six' %}

{% snippet 'extras_random_everything_seven' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

### Random element from a list/array

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_everything_eight' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

### Random UUID

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_everything_nine' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

### Unique Random Values

Just like most data providers, `Faker#random` supports generation of unique values. See {{ anchor(title='Generator of Unique Values', collectionType='wiki', collectionId='', itemId='Generator of Unique Values') }} page for usage details.

Both "local" (provider level) and "global" (faker level) generation of unique values are supported for `RandomProvider`:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_everything_ten' %}
```

```kotlin
{% snippet 'extras_random_everything_eleven' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

{% btc %}{% endbtc %}

<br>

## Random Strings from Templates

Faker's `StringProvider` allows for replacing certain user-defined parts of strings with randomly generated chars (letters and digits), as well as generating strings from regex expressions. 
The following functions are available withing the `Faker().string`:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'extras_random_strings_from_templates_zero' %}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

{% btc %}{% endbtc %}

<br>
