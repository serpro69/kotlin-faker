---
title: lebowski
faker: movies
---

## `Faker().lebowski`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/lebowski.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().lebowski.actors() // => Jeff Bridges

    Faker().lebowski.characters() // => The Dude

    Faker().lebowski.quotes() // => He's a good man...and thorough.
    ```
