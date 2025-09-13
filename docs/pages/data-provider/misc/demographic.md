---
title: demographic
faker: misc
---

## `Faker().demographic`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/demographic.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().demographic.race() // => American Indian or Alaska Native

    Faker().demographic.sex() // => Male

    Faker().demographic.demonym() // => Afghan

    Faker().demographic.educationalAttainment() // => No schooling completed

    Faker().demographic.maritalStatus() // => Married
    ```
