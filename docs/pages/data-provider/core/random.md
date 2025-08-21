---
title: random
---

## `Faker().random`

Provides data-generator-like functionality for the functions of `RandomService`, which is a "wrapper" around `java.util.Random`.

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().random.nextInt()

    Faker().random.nextInt(bound = 42)

    Faker().random.nextInt(intRange = 0..42)

    Faker().random.nextInt(min = 0, max = 42)

    Faker().random.nextLong()

    Faker().random.nextLong(bound = 42)

    Faker().random.nextLong(longRange = 0..42)

    Faker().random.nextLong(min = 0, max = 42)

    Faker().random.nextDouble()

    Faker().random.nextChar()

    Faker().random.nextEnum(MyEnum::class)

    Faker().random.nextBoolean()

    Faker().random.randomValue(listOf(1, 2, 3))

    Faker().random.randomString()

    Faker().random.nextUUID()

    Faker().random.randomSublist(listOf(1, 2, 3, 4, 5, 6))

    Faker().random.randomPastDate()

    Faker().random.randomPastDate(min = Instant.ofEpochSecond(0))

    Faker().random.randomFutureDate()

    Faker().random.randomFutureDate(max = Instant.now().plus(Duration.ofDays(7)))

    Faker().random.randomDate(min = Instant.ofEpochSecond(0), max = Instant.now(), zoneOffset = ZoneOffset.UTC)

    // and more
    ```
