---
---

# Kotest Property Extension

## TOC

- [About](#about)
- [Usage](#usage)
  - [Installation](#installation)
  - [Generate Arb Extensions](#generate-arb-extensions)
  - [Random Class Instance ARBs](#random-class-instance-arb)

## About

Kotlin-faker `kotest-property` and `kotest-property-ksp` artifacts provide faker-based [`Arb` generators](https://kotest.io/docs/proptest/property-test-generators.html) extensions via [KSP](https://kotlinlang.org/docs/ksp-overview.html) compiler plugin for [kotest property testing](https://kotest.io/docs/proptest/property-based-testing.html).

## Usage

### Installation

`kotest-property` extension builds upon [KSP](https://kotlinlang.org/docs/ksp-overview.html), from which it inherits easy integration with Gradle. To use this extension, add the following in your `build.gradle.kts`:

- ① add the ksp plugin (You can check the latest version in their [releases](https://github.com/google/ksp/releases/).)
- ② add the core `kotlin-faker` dependency to the test classpath
- ③ add the `testImplementation` dependency for the `kotest-property` extension
- ④ add the `kspTest` dependency for the `kotest-property-ksp` extension
    - This will generate the code for test sources. If you're using kotlin-faker for something other than testing (e.g. data anonymization) and want to generate extension code for main source instead, use `ksp` configuration for this dependency.
- ⑤ the core `kotlin-faker` dependency also needs to be added to `kspTest` configuration

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}

```kotlin
plugins {
    id("com.google.devtools.ksp") version "$kspVersion" // ①
}

dependencies {
    testImplementation("io.github.serpro69:kotlin-faker:$fakerVersion") // ②
    testImplementation("io.github.serpro69:kotlin-faker-kotest-property:$fakerExtVersion") // ③
    kspTest("io.github.serpro69:kotlin-faker-kotest-property-ksp:$fakerExtVersion") // ④
    kspTest("io.github.serpro69:kotlin-faker:$fakerVersion") // ⑤
}
```

{% endfilter %}
{% endkotlin %}

{% endtabs %}

{% btc %}{% endbtc %}

<br>

### Generate Arb Extensions

① To generate `Arb` extensions for Fakers, use the `FakerArb` annotation on the test file and provide the "Faker" classes to generate extensions for:

```kotlin
@file:FakerArb(Faker::class, BooksFaker::class, EduFaker::class) // ①

package com.example
```

{% info %}
{% filter compileAs('md') %}
The annotation only needs to be used once, and you can even use it in a separate empty file in your test sources
{% endfilter %}
{% endinfo %}

<br>

{% warn %}
{% filter compileAs('md') %}
For any additional fakers that you want to generate `Arb`s for, e.g. `BooksFaker` or `EduFaker`, make sure to add the corresponding dependency to both `testImplementation` and `kspTest` configurations:

```kotlin
dependencies {
    testImplementation("io.github.serpro69:kotlin-faker-books:$fakerVersion")
    testImplementation("io.github.serpro69:kotlin-faker-edu:$fakerVersion")
    kspTest("io.github.serpro69:kotlin-faker-books:$fakerVersion")
    kspTest("io.github.serpro69:kotlin-faker-edu:$fakerVersion")
}
```

{% endfilter %}
{% endwarn %}

The plugin will generate [`Arb` generator](https://kotest.io/docs/proptest/property-test-generators.html) extensions for all specified faker classes and their data providers.

① Each `Faker` instance will have an `arb` property that provides access to standard faker data generators, but which return data wrapped in `Arb` instances.

② Generated code will also include `faker` extension properties for `Arb.Companion` that exposes the same functionality.

③ Each generated `ArbFaker` instance will include all the standard Faker data generator properties, i.e. `address`, `color`, `currency`, etc. for the "core" Faker.

④ Each Arb-based data provider, e.g. `ArbAddress` that "implements" [`Address` data provider]({{ link(collectionType='pages', collectionId='data-provider', itemId='Address') }}), will include all functions for that given data provider, but returned as parameterized `Arb` types.

```kotlin
public val Faker.arb: ArbFaker // ①
    get() = ArbFaker(this)
public val Arb.Companion.faker: ArbFaker // ②
    get() = io.github.serpro69.kfaker.ArbFaker(Faker())

public val BooksFaker.arb: ArbBooksFaker // ①
    get() = ArbBooksFaker(this)
public val Arb.Companion.booksFaker: ArbBooksFaker // ②
    get() = io.github.serpro69.kfaker.books.ArbBooksFaker(BooksFaker())

public class ArbFaker(private val faker: Faker) { // ③
    public val address: ArbAddress by lazy { ArbAddress(faker.address) }

    public val color: ArbColor by lazy { ArbColor(faker.color) }

    public val currency: ArbCurrency by lazy { ArbCurrency(faker.currency) }

    // ...
}

public class ArbAddress internal constructor(private val address: Address) { // ④
    public fun city(): Arb<String> = arbitrary { address.city() }

    public fun country(): Arb<String> = arbitrary { address.country() }

    // ...
}
```

<br>

This can then be used with standard [Kotest property testing](https://kotest.io/docs/proptest/property-based-testing.html) functionality, just like the built-in Arbs, e.g. with [property test functions](https://kotest.io/docs/proptest/property-test-functions.html) like `forAll`:

```kotlin
package com.example

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.arb
import io.github.serpro69.kfaker.books.BooksFaker
import io.github.serpro69.kfaker.books.arb
import io.github.serpro69.kfaker.books.booksFaker
import io.github.serpro69.kfaker.edu.EduFaker
import io.github.serpro69.kfaker.edu.arb
import io.github.serpro69.kfaker.faker
import io.github.serpro69.kfaker.kotest.FakerArb
import io.github.serpro69.kfaker.randomClass
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.property.Arb
import io.kotest.property.forAll

class KotestPropertyArbsTest : DescribeSpec({
    describe("Custom kotlin-faker Arbs") {
        it("should generate quotes from the bible") {
            val b = BooksFaker()
            forAll(b.arb.bible.quote()) { q: String ->
                q.isNotBlank()
            }
        }
        it("should generate addresses") {
            val f = Faker()
            forAll(f.arb.address.city()) { q ->
                q.isNotBlank()
            }
            forAll(f.arb.address.city(), f.arb.address.streetName()) { city, street ->
                city.isNotBlank()
                street.isNotBlank()
            }
        }
        it("should generate quotes from companion object") {
            forAll(Arb.booksFaker.bible.quote()) { q: String ->
                q.isNotBlank()
            }
        }
        it("should generate addresses from companion object") {
            class Address(val city: String, val state: String) {
                fun isValid() = city.isNotBlank() && state.isNotBlank()
            }
            forAll(Arb.faker.address.city(), Arb.faker.address.state()) { city, state ->
                Address(city, state).isValid()
            }
        }
    }
})
```

{% btc %}{% endbtc %}

<br>

### Random Class Instance ARB

The `kotlin-faker-kotest-property` extension additionally adds a `randomClass` extension property to `Arb.Compaion` for generating a random instance of any class, which provides the same functionality as the default [Random Class Instance]({{ link(collectionType='wiki', collectionId='', itemId='Extras') }}##random-instance-of-any-class) faker functionality, but wrapped in `Arb` type to be used with kotest property testing.

```kotlin
it("should generate person with address") {
    val f = Faker()
    val person: () -> Arb<Person> = {
        Arb.randomClass.instance<Person> {
            namedParameterGenerator("name") { f.name.name() }
            namedParameterGenerator("age") { f.random.nextInt(20, 30) }
        }
    }
    val address: () -> Arb<Address> = {
        Arb.randomClass.instance<Address> {
            namedParameterGenerator("city") { f.address.city() }
            namedParameterGenerator("streetName") { f.address.streetName() }
            namedParameterGenerator("streetAddress") { f.address.streetAddress() }
        }
    }
    forAll(person(), address()) { p: Person, a: Address ->
        p.name.isNotBlank()
        p.age in 20..30
        a.city.isNotBlank()
        a.streetName.isNotBlank()
        a.streetAddress.isNotBlank()
    }
}
```

{% btc %}{% endbtc %}

<br>
