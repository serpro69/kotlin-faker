---
title: dcComics
---

## `Faker().dcComics`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/dc_comics.yml:dc_comics_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().dcComics.hero() // => Batman

    Faker().dcComics.heroine() // => Wonder Woman

    Faker().dcComics.villain() // => The Joker

    Faker().dcComics.name() // => John Stewart

    Faker().dcComics.title() // => The Sinestro Corps War
    ```
