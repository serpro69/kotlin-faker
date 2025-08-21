---
title: randomProvider
---

## `Faker().randomProvider`

Provides functionality for creating a random instance of any (almost any) class.

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().randomProvider.randomClassInstance<String>() // Random instance of a String class

    Faker().randomProvider.randomClassInstance<Foo>() // Random instance of a Foo class
    ```
