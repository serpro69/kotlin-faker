---
title: esport
---

## `Faker().esport`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/esport.yml:esport_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().esport.players() // => Froggen

    Faker().esport.teams() // => Dignitas

    Faker().esport.events() // => ESL Cologne

    Faker().esport.leagues() // => ESL

    Faker().esport.games() // => CS:GO
    ```
