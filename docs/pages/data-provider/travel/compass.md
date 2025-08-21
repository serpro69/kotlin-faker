---
title: compass
---

## `Faker().compass`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/compass.yml:compass_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    ----
    ====

    '''

    .Non-implemented Functions:
    [%collapsible]
    ====
    [source,kotlin]
    ----
    Faker().compass.direction() // => north

    Faker().compass.abbreviation() // => N

    Faker().compass.azimuth() // => 180
    ```
