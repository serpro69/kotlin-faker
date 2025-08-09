---
---

# Generator of Unique Values

<strong>kotlin-faker</strong> supports generation of unique (non-repeatable) values and there are two ways to use this: _"globally"_ - per data-provider, and _"locally"_ - per function.

## Unique Values for Entire Data Provider

❶ Use the `faker.unique.configuration` function to configure unique generation of values "globally".

❷ Enable generation of unique values for `Address` data provider

❸ Each invocation of `faker.address.<someFun>()` will generate a unique value.

❹ Repeated invocations will produce unique values until all the values are exhausted and a `uniqueGeneratorRetryLimit` is reached.

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'unique_data_generator_one' %}
```
{% endfilter %}
{% endkotlin %}

{% java "Java" %}
{% filter compileAs('md') %}
```java
{% snippet 'unique_data_generator_one_java' %}
```
{% endfilter %}
{% endjava %}

{% endtabs %}

{% warn %}
Unique values can be exhausted and <code>Faker</code> will throw a <code>RetryLimitException</code> if the <code>uniqueGeneratorRetryLimit</code> is reached.
<br>
See also {{ anchor(title='Faker Configuration', collectionType='wiki', collectionId='', itemId='Faker Configuration') }} page to learn how to configure <code>Faker</code>, as well as the <a href="{{ link(collectionType='wiki', collectionId='', itemId='Generator of Unique Values') }}#configuring-retry-limit">Configuring Retry Limit</a> section of this page for information on retry limits for unique generators.
{% endwarn %}

{% btc %}{% endbtc %}

### Clearing Records of Generated Values

It is possible to clear (reset) the record of already generated values, so that they can be generated again, and thus minimize the chances of reaching the retry limit of generating unique values.

❶ Use the `faker.unique.clear(faker::<provider>)` function to clear the record of generated values for the particular `<provider>`, thus increasing the overall pool of possible values.

❷ Invocations of `faker.<provider>.<someFun>()` can now generate values that were already generated before.

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'unique_data_generator_two' %}
```
{% endfilter %}
{% endkotlin %}

{% java "Java" %}
{% filter compileAs('md') %}
```java
{% snippet 'unique_data_generator_two_java' %}
```
{% endfilter %}
{% endjava %}

{% endtabs %}

---

It is also possible to clear all records of generated values:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'unique_data_generator_three' %}
{% snippet 'unique_data_generator_four' %}
```
{% endfilter %}
{% endkotlin %}

{% java "Java" %}
{% filter compileAs('md') %}
```java
{% snippet 'unique_data_generator_three_java' %}
```
{% endfilter %}
{% endjava %}

{% endtabs %}

{% btc %}{% endbtc %}

### Disabling Unique Values Generator

One may want to disable generating unique values altogether. Just like clearing records of generated unique values, disabling unique generation can also be done on a provider-level:

❶ Unique generation is enabled for `Address`, `Name`, and `Internet`.

❷ Unique generation is then disabled for `Address`.

❸ `Address` does not generate unique values anymore.

❹ `Name` and `Internet` still generate unique values.

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'unique_data_generator_five' %}
```
{% endfilter %}
{% endkotlin %}

{% java "Java" %}
{% filter compileAs('md') %}
```java
{% snippet 'unique_data_generator_five_java' %}
```
{% endfilter %}
{% endjava %}

{% endtabs %}

As well as for all providers:

❶ Unique generation is disabled for `Address`, `Name`, `Internet`, and all other providers that may have been enabled.

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'unique_data_generator_six' %}
```
{% endfilter %}
{% endkotlin %}

{% java "Java" %}
{% filter compileAs('md') %}
```java
{% snippet 'unique_data_generator_six_java' %}
```
{% endfilter %}
{% endjava %}

{% endtabs %}

{% info %}
Disabling generation of unique values will effectively clear the record(s) of already generated values.
{% endinfo %}

{% btc %}{% endbtc %}

<br>

## Unique Values for a Single Function

Unique values can also be generated for a single function, instead of an entire data provider. To do so, prepend the function invocation with `unique` generator property:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'unique_data_generator_seven' %}
```
{% endfilter %}
{% endkotlin %}

{% java "Java" %}
{% filter compileAs('md') %}
```java
{% snippet 'unique_data_generator_seven_java' %}
```
{% endfilter %}
{% endjava %}

{% endtabs %}

```kotlin
val faker = Faker()

repeat(10) { faker.address.unique.country() } // will generate unique country each time `country()` is prefixed with `unique`

repeat(10) { faker.address.city() } // this will not necessarily be unique (unless `faker.unique.enable(faker::address)` was called previously)
```

---

To clear the record of unique values that were already generated use the `clear("functionName")` with the "local" unique generator:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
{% snippet 'unique_data_generator_seven' %}
```
{% endfilter %}
{% endkotlin %}

{% java "Java" %}
{% filter compileAs('md') %}
```java
{% snippet 'unique_data_generator_seven_java' %}
```
{% endfilter %}
{% endjava %}

{% endtabs %}

```kotlin
faker.address.unique.clear("city") // clears used values for `faker.address.unique.city()` function

faker.address.unique.clearAll() // clears used values for all functions of address provider
```

{% info %}
There is no <code>disable</code> function available for local unique generators - they are completely independent of the standard APIs:

{% tabs %}
{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
// generates unique city each time it's called
faker.address.unique.city()

// generates non-unique city (unless global unique generator for 'address' is enabled)
faker.address.city()
```
{% endfilter %}
{% endkotlin %}
{% endtabs %}
{% endinfo %}

{% btc %}{% endbtc %}

<br>

## Configuring retry limit

If the retry count of unique generator exceeds the configured value (defaults to `100`) then a `RetryLimitException` will be thrown.

It is possible to re-configure the default value through `FakerConfig`:

```kotlin
val config = fakerConfig {
    uniqueGeneratorRetryLimit = 1000
}

val faker = Faker(config)
```

{% info %}
Even if <code>uniqueGeneratorRetryLimit</code> is set to a very high number, one still needs to keep in mind the dataset size for a given faker function to avoid unexpected exceptions when generating unique values.
<br>
For example, a <code>city_prefix</code> param in the <code>address</code> category only has 7 values:
{% filter compileAs('html') %}
<section class="accordions">
  <article class="accordion">
    <div class="accordion-header toggle">
      <p>address.yml</p>
    </div>
    <div class="accordion-body">
      <div class="accordion-content">
{% filter compileAs('md') %}

```yaml
en:
  faker:
    address:
      city_prefix: [North, East, West, South, New, Lake, Port]
# rest of the address.yml dict file
```

{% endfilter %}
      </div>
    </div>
  </article>
</section>
{% endfilter %}
Therefore, the initial pool size of unique values is quite small and will be exhausted very quickly, so the retry limit might need to be set to a higher than default value.
<br>
And obviously if one tries to generate 8 unique city prefixes a <code>RetryLimitException</code> will be thrown sooner or later, irrespective of what value is set for the <code>uniqueGeneratorRetryLimit</code> config property.
<br>
<br>
A reference list of available Data Providers with their corresponding yml dictionary data can be found on the {{ anchor(title='Data Providers', collectionType='wiki', collectionId='', itemId='Data Providers') }} wiki page.
{% endinfo %}

{% info %}
It is also worth mentioning that "global" and "local" unique data generators are independent of each other, each keeping its own records of generated values that might need to be cleared eventually.
<br>
At the same time, the <code>uniqueGeneratorRetryLimit</code> config property applies to both equally.
{% endinfo %}

{% btc %}{% endbtc %}

<br>

## Excluding Values from Generation

Certain values can be excluded from being generated with unique generator. This is configured on the `faker` ("global") level for each (or in some cases - all) of the providers.

```kotlin
val faker = Faker()

faker.unique.configuration {
    // Enable generation of unique values for Address provider
    // Any unique generation configuration will only affect "enabled" providers
    enable(faker::address)

    // Exclude listOfValues from being generated 
    // in all providers that are enabled for unique generation
    exclude(listOfValues)

    // Exclude values starting with "A" from being generated 
    // in all providers that are enabled for unique generation
    exclude { listOf(Regex("^A")) }

    // Additional configuration for particular provider
    // First enable generation of unique values for Name provider
    enable(faker::name) {
        // Exclude listOfNames from being generated by any Name provider function
        excludeFromProvider<Name>(listOfNames)

        // Exclude listOfLastNames from being generated by Name#lastName function
        excludeFromFunction(Name::lastName, listOfLastNames)

        // Exclude values starting with "B" from being generated by any Name provider function
        excludeFromProvider<Name> { listOf(Regex("^B")) }

        // Exclude values starting with "C" from being generated by Name#country function
        excludeFromFunction(Name::lastName) { listOf(Regex("^C")) }
    }
}

// Based on the above config the following will be true in addition to generating unique values:
val city = faker.address.city()
assertTrue(listOfValues.contains(city) == false)
assertTrue(city.startsWith("A") == false)

val firstName = faker.name.firstName()
val lastName = faker.name.lastName()
assertTrue(listOfValues.contains(firstName) == false)
assertTrue(listOfValues.contains(lastName) == false)
assertTrue(listOfNames.contains(firstName) == false)
assertTrue(listOfNames.contains(lastName) == false)
assertTrue(listOfLastNames.contains(lastName) == false)
assertTrue(firstName.startsWith("A") == false)
assertTrue(lastName.startsWith("A") == false)
assertTrue(firstName.startsWith("B") == false)
assertTrue(lastName.startsWith("B") == false)
assertTrue(lastName.startsWith("C") == false)
```

{% info %}
This is only applicable when the whole category, i.e. <code>Address</code> or <code>Name</code>, is enabled for unique generation of values. Local generators will still generate unique values of their own, but won't take into consideration exclusion rules, if any are set:

{% tabs %}
{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
val faker = Faker()

faker.unique.configuration {
    enable(faker::address)
    exclude(listOfCountries)
}

// generates unique countries, but will never generate any of the values from the 'listOfCountries'
faker.address.country()

// will still generate its own unique countries, but won't consider the exclusions that are set above
faker.address.unique.country()
```
{% endfilter %}
{% endkotlin %}
{% endtabs %}
{% endinfo %}

{% btc %}{% endbtc %}

<br>
