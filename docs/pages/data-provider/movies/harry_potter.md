---
title: harryPotter
faker: movies
---

## `Faker().harryPotter`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/harry_potter.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().harryPotter.characters() // => Hannah Abbott

    Faker().harryPotter.locations() // => The Burrow

    Faker().harryPotter.quotes() // => It does not do to dwell on dreams and forget to live.

    Faker().harryPotter.books() // => Harry Potter and the Sorcerer's Stone

    Faker().harryPotter.houses() // => Gryffindor

    Faker().harryPotter.spells() // => Accio
    ```
