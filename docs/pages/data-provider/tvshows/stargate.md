---
title: stargate
faker: tvshows
---

## `Faker().stargate`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/stargate.yml:stargate_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().stargate.characters() // => Jack O'Neill

    Faker().stargate.planets() // => Abydos

    Faker().stargate.quotes() // => What is an Oprah?
    ```
