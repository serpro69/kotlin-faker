---
title: cat
faker: creatures
---

## `Faker().cat`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/cat.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().cat.name() // => Alfie

    Faker().cat.breed() // => Abyssinian

    Faker().cat.registry() // => American Cat Fanciers Association
    ```
