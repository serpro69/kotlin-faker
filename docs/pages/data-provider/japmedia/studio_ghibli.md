---
title: studioGhibli
faker: japmedia
---

## `Faker().studioGhibli`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/studio_ghibli.yml:studio_ghibli_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().studioGhibli.characters() // => Chihiro Ogino

    Faker().studioGhibli.quotes() // => It’s not really important what color your dress is. What matters is the heart inside.

    Faker().studioGhibli.movies() // => Spirited Away
    ```
