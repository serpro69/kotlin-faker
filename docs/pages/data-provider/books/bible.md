---
title: bible
faker: books
---

## `Faker().bible`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/bible.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().bible.character() // => Adam

    Faker().bible.location() // => Egypt

    Faker().bible.quote() // => I am the way and the truth and the life. No one comes to the Father except through me.
    ```
