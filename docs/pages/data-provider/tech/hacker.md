---
title: hacker
faker: tech
---

## `Faker().hacker`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/hacker.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().hacker.abbreviation() // => TCP

    Faker().hacker.adjective() // => auxiliary

    Faker().hacker.noun() // => driver

    Faker().hacker.verb() // => back up

    Faker().hacker.ingverb() // => backing up
    ```
