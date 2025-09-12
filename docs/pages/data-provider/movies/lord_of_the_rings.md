---
title: lordOfTheRings
faker: movies
---

## `Faker().lordOfTheRings`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/lord_of_the_rings.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().lordOfTheRings.characters() // => Frodo Baggins

    Faker().lordOfTheRings.locations() // => Aglarond

    Faker().lordOfTheRings.quotes() // => Often does hatred hurt itself!
    ```
