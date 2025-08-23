---
title: markdown
faker: lorem
---

## `Faker().markdown`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/markdown.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().markdown.headers() // => #
    Faker().markdown.emphasis() // => _
    ```
