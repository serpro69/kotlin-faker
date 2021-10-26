---
---

# Extras

`kotlin-faker` provides additional functionality outside of data generation from static .yml dictionaries.

## ToC

* [Random instance of any class](#random-instance-of-any-class)
  * [Pre-configuring type generation for constructor arguments](#pre-configuring-type-generation-for-constructor-arguments)
  * [Deterministic constructor selection](#deterministic-constructor-selection)
* [Random Everything](#random-everything)

<br>

## Random instance of any class

It is possible to create a random instance of (almost) any class. 

There are some rules to keep in mind:

- By default, the constructor with the least number of arguments is used (This can be configured - read on.)
- `kotlin.collection.*` and `kolin.Array` types in the constructor are not supported at the moment

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

### Pre-Configuring type generation for constructor arguments

Some, or all, of the constructor params can be instantiated with values following some pre-configured logic using `typeGenerator` function. Consider the following example:

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

```
baz.id == 0
baz.uuid == UUID.fromString("00000000-0000-0000-0000-000000000000")
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
    typeGenerator<String> { faker.name.fullName() }
}
```
{% endfilter %}
{% endkotlin %}

{% endtabs %}

{% btc %}{% endbtc %}

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

## Random Everything

Faker provides its wrapper functions around `java.util.Random` (with some additional functionality that is not covered by `java.util.Random`) through `Faker().random` property.

==TODO examples==

{% btc %}{% endbtc %}

<br>
