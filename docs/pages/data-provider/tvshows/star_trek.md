---
title: star_trek
---

## `Faker().star_trek`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/star_trek.yml:star_trek_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().starTrek.character() // => James Tiberius Kirk

    Faker().starTrek.location() // => Qo'noS

    Faker().starTrek.specie() // => Breen

    Faker().starTrek.villain() // => Q
    ```
