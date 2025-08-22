---
title: breakingBad
faker: tvshows
---

## `Faker().breakingBad`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/breaking_bad.yml:breaking_bad_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().breakingBad.character() // => Walter White

    Faker().breakingBad.episode() // => Pilot
    ```
