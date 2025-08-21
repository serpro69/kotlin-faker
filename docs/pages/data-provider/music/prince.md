---
title: prince
---

## `Faker().prince`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/prince.yml:prince_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker.prince.lyric() // => Only want to see you laughing in the purple rain.
    Faker.prince.song() // => 1-800-Newfunk Ad
    Faker.prince.album() // => For You
    Faker.prince.band() // => The Revolution
    ```
