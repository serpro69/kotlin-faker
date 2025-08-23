---
title: book
faker: books
---

## `Faker().book`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/book.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().book.title() // => Absalom, Absalom!

    Faker().book.author() // => John Smith

    Faker().book.publisher() // => Academic Press

    Faker().book.genre() // => Classic
    ```
