---
title: australia
---

## `Faker().australia`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/australia.yml:australia_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().australia.locations() // => Brisbane

    Faker().australia.animals() // => Koala

    Faker().australia.states() // => New South Wales
    ```
