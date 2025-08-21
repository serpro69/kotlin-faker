---
title: bossaNova
---

## `Faker().bossaNova`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/bossa_nova.yml:bossa_nova_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().bossaNova.artists() // => Alaide Costa

    Faker().bossaNova.songs() // => A Banda
    ```
