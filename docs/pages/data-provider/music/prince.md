---
title: prince
faker: music
---

## `Faker().prince`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/prince.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker.prince.lyric() // => Only want to see you laughing in the purple rain.
    Faker.prince.song() // => 1-800-Newfunk Ad
    Faker.prince.album() // => For You
    Faker.prince.band() // => The Revolution
    ```
