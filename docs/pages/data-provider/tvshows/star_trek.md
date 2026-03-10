---
title: star_trek
faker: tvshows
---

## `Faker().star_trek`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/star_trek.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().starTrek.character() // => James Tiberius Kirk

    Faker().starTrek.location() // => Qo'noS

    Faker().starTrek.specie() // => Breen

    Faker().starTrek.villain() // => Q
    ```
