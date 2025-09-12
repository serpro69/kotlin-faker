---
title: simpsons
faker: tvshows
---

## `Faker().simpsons`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/simpsons.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().simpsons.characters() // => Homer Simpson

    Faker().simpsons.locations() // => Springfield

    Faker().simpsons.quotes() // => Marriage is like a coffin and each kid is another nail.

    Faker().simpsons.episodeTitles() // => Simpsons Roasting on an Open Fire
    ```
