---
title: yoda
faker: movies
---

## `Faker().yoda`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/yoda.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().yoda.quotes() // => Use your feelings, Obi-Wan, and find him you will.
    ```
