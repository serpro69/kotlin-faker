---
title: chuckNorris
---

## `Faker().chuckNorris`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/chuck_norris.yml:chuck_norris_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().chuckNorris.fact() // => All arrays Chuck Norris declares are of infinite size, because Chuck Norris knows no bounds.
    ```
