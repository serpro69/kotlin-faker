---
title: parksAndRec
---

## `Faker().parksAndRec`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/parks_and_rec.yml:parks_and_rec_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().parksAndRec.characters() // => Leslie Knope

    Faker().parksAndRec.cities() // => Pawnee
    ```
