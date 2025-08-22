---
title: theThickOfIt
faker: tvshows
---

## `Faker().theThickOfIt`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/the_thick_of_it.yml:the_thick_of_it_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().theThickOfIt.characters() // => Malcolm Tucker

    Faker().theThickOfIt.positions() // => General Elections Advisor

    Faker().theThickOfIt.departments() // => Number 10
    ```
