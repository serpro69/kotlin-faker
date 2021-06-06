---
---

# Faker Configuration

## Default configuration

`Faker` can be configured through the `FakerConfig` class, either by passing an instance of `FakerConfig` directly, or  

If no `fakerConfig` instance is passed to `Faker` constructor, or to the `faker` builder DSL, then default configuration will be used:

- `locale == "en"`
- `random` is seeded with a pseudo-randomly generated number.
- `uniqueGeneratorRetryLimit` is set to `100`

## Deterministic Random

Faker supports seeding of it's PRNG (pseudo-random number generator) through `FakerConfig` to provide deterministic output of repeated function invocations.

```kotlin
{% snippet 'faker_config_one' %}
```

Alternatively a `randomSeed` property can be used instead of passing an instance of `java.util.Random`:
```kotlin
{% snippet 'faker_config_two' %}
```

---

{% note %}
{% filter compileAs('md') %}
`randomSeed` config property has precedence over `random` property, and the latter will be ignored if `randomSeed` is specified.
{% endfilter %}
{% endnote %}

```kotlin
{% snippet 'faker_config_three' %}
```

---

