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

`kotlin-faker-kotest-property` artifact extends [`Arb` generators](https://kotest.io/docs/proptest/property-test-generators.html) and provides an easy way to use kotlin-faker functionality with [kotest property testing](https://kotest.io/docs/proptest/property-based-testing.html).

## Usage

### Installation

`kotlin-faker-kotest-property` extension needs to be added in your `build.gradle.kts` alongside core `kotlin-faker` dependency:

- ① add the core `kotlin-faker` dependency to the test classpath
- ② add the `testImplementation` dependency for the `kotlin-faker-kotest-property` extension



=== "kotlin"


```kotlin
dependencies {
    testImplementation("io.github.serpro69:kotlin-faker:$fakerVersion") // ①
    testImplementation("io.github.serpro69:kotlin-faker-kotest-property:$fakerExtVersion") // ②
}
```








<br>

### Arb Extensions

To get an `Arb` instance of any fake generator, use `Arb.of` extension function:

```kotlin
Arb.of(Faker().address::city)
```

<br>

{% info %}

For any additional fakers that you want to generate `Arb`s for, e.g. `BooksFaker` or `EduFaker`, make sure to add the corresponding dependency to `testImplementation`:

```kotlin
dependencies {
    testImplementation("io.github.serpro69:kotlin-faker-books:$fakerVersion")
    testImplementation("io.github.serpro69:kotlin-faker-edu:$fakerVersion")
}
```


{% endinfo %}

<br>

---

<br>

This can then be used with standard [Kotest property testing](https://kotest.io/docs/proptest/property-based-testing.html) functionality, just like the built-in Arbs, e.g. with [property test functions](https://kotest.io/docs/proptest/property-test-functions.html) like `forAll`:

```kotlin
class KotestPropertyArbsTest : DescribeSpec({
    describe("Custom kotlin-faker Arbs") {
        it("should generate quotes from the bible") {
            val b = BooksFaker()
            forAll(Arb.of(b.bible::quote)) { q: String ->
                q.isNotBlank()
            }
        }
        it("should generate addresses") {
            val f = Faker()
            forAll(Arb.of(f.address::city)) { c: String ->
                c.isNotBlank()
            }
            forAll(Arb.of(f.address::city), Arb.of(f.address::streetName)) { city: String, street: String ->
                city.isNotBlank()
                street.isNotBlank()
            }
        }
    }
})
```



<br>

### Random Class Instance ARB

It is possible to generate [Random Class Instances]({{ link(collectionType='wiki', collectionId='', itemId='Extras') }}##random-instance-of-any-class) in a similar way:

```kotlin
it("should generate person with address") {
    val f = Faker()
    f.randomClass.configure {
        namedParameterGenerator("name") { f.name.name() }
        namedParameterGenerator("age") { f.random.nextInt(20, 30) }
        namedParameterGenerator("city") { f.address.city() }
        namedParameterGenerator("streetName") { f.address.streetName() }
        namedParameterGenerator("streetAddress") { f.address.streetAddress() }
    }

    forAll(Arb.of(f.randomClass::randomClassInstance), Arb.of(f.randomClass::randomClassInstance)) { p: Person, a: Address ->
        p.name.isNotBlank()
        p.age in 20..30
        a.city.isNotBlank()
        a.streetName.isNotBlank()
        a.streetAddress.isNotBlank()
    }
}
```



<br>
