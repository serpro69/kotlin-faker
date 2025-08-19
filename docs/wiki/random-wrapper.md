---
icon: material/ice-cream
---

# :material-ice-cream: Random Everything

Faker provides its wrapper functions around `java.util.Random` (with some additional functionality that is not covered by `java.util.Random`) through `Faker().random` property.

### Wrappers around `java.util.Random`



=== "kotlin"

```kotlin
{% snippet 'extras_random_everything_one' %}
```





### Random Enum Instance



=== "kotlin"

```kotlin
{% snippet 'extras_random_everything_two' %}

{% snippet 'extras_random_everything_three' %}
```





### Random Strings



=== "kotlin"

```kotlin
{% snippet 'extras_random_everything_four' %}

{% snippet 'extras_random_everything_five' %}
```





### Random sub-lists and sub-sets



=== "kotlin"

```kotlin
{% snippet 'extras_random_everything_six' %}

{% snippet 'extras_random_everything_seven' %}
```





### Random element from a list/array



=== "kotlin"

```kotlin
{% snippet 'extras_random_everything_eight' %}
```





### Random UUID



=== "kotlin"

```kotlin
{% snippet 'extras_random_everything_nine' %}
```





### Unique Random Values

Just like most data providers, `Faker#random` supports generation of unique values. See {{ anchor(title='Generator of Unique Values', collectionType='wiki', collectionId='', itemId='Generator of Unique Values') }} page for usage details.

Both "local" (provider level) and "global" (faker level) generation of unique values are supported for `RandomProvider`:



=== "kotlin"

```kotlin
{% snippet 'extras_random_everything_ten' %}
```

```kotlin
{% snippet 'extras_random_everything_eleven' %}
```







<br>

