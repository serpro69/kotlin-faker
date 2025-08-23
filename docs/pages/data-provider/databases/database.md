---
title: database
faker: databases
---

## `Faker().database`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/database.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().database.columnName() // => id

    Faker().database.mongodbObjectId() // => 65e652700000000000000000
    ```
