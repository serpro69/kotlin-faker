---
title: dcComics
faker: books
---

## `Faker().dcComics`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/dc_comics.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().dcComics.hero() // => Batman

    Faker().dcComics.heroine() // => Wonder Woman

    Faker().dcComics.villain() // => The Joker

    Faker().dcComics.name() // => John Stewart

    Faker().dcComics.title() // => The Sinestro Corps War
    ```
