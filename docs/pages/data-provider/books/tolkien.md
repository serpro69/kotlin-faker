---
title: tolkien
---

## `Faker().tolkien`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/tolkien.yml:tolkien_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().tolkien.poems() // => Chip the glasses and crack the plates
    Faker().tolkien.locations() // => Abyss
    Faker().tolkien.races() // => Ainur
    Faker().tolkien.characters() // => Adalbert Bolger

    Faker().tolkien.lordOfTheRings.characters() // => Frodo Baggins
    Faker().tolkien.lordOfTheRings.locations() // => Aglarond
    Faker().tolkien.lordOfTheRings.quotes() // => Often does hatred hurt itself!

    Faker().tolkien.hobbit.character() // => Bilbo Baggins
    Faker().tolkien.hobbit.thorinsCompany() // => Thorin Oakenshield
    Faker().tolkien.hobbit.quote() // => Do you wish me a good morning, or mean that it is a good morning whether I want it or not; or that you feel good this morning; or that it is a morning to be good on?
    Faker().tolkien.hobbit.location() // => Bree
    ```
