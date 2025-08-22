---
title: lordOfTheRings
faker: movies
---

## `Faker().lordOfTheRings`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/lord_of_the_rings.yml:lord_of_the_rings_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().lordOfTheRings.characters() // => Frodo Baggins

    Faker().lordOfTheRings.locations() // => Aglarond

    Faker().lordOfTheRings.quotes() // => Often does hatred hurt itself!
    ```
