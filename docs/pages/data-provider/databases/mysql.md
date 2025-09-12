---
title: mySQL
faker: databases
---

## `Faker().mySQL`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/mysql.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().mySQL.dataType() // => VARCHAR

    Faker().mySQL.engine() // => InnoDB
    ```