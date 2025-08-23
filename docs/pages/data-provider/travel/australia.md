---
title: australia
faker: travel
---

## `Faker().australia`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/australia.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().australia.locations() // => Brisbane

    Faker().australia.animals() // => Koala

    Faker().australia.states() // => New South Wales
    ```
