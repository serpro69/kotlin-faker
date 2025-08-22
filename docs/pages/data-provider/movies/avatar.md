---
title: avatar
faker: movies
---

## `Faker().avatar`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/avatar.yml:avatar_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().avatar.characters() // => Neytiri

    Faker().avatar.dates() // => December 18, 2009

    Faker().avatar.quotes() // => I'm a warrior of the Jarhead clan.
    ```
