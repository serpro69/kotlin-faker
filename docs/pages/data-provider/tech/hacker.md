---
title: hacker
---

## `Faker().hacker`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/hacker.yml:hacker_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().hacker.abbreviation() // => TCP

    Faker().hacker.adjective() // => auxiliary

    Faker().hacker.noun() // => driver

    Faker().hacker.verb() // => back up

    Faker().hacker.ingverb() // => backing up
    ```
