---
title: artist
---

## `Faker().artist`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/artist.yml:artist_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().artist.names() // => Donatello
    ```
