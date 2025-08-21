---
title: seinfeld
---

## `Faker().seinfeld`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/seinfeld.yml:seinfeld_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().seinfeld.character() // => George Costanza

    Faker().seinfeld.quote() // => I'm not a lesbian. I hate men, but I'm not a lesbian.

    Faker().seinfeld.business() // => Champagne Video
    ```
