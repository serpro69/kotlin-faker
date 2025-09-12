---
title: parksAndRec
faker: tvshows
---

## `Faker().parksAndRec`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/parks_and_rec.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().parksAndRec.characters() // => Leslie Knope

    Faker().parksAndRec.cities() // => Pawnee
    ```
