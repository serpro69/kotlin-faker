---
title: mariaDB
faker: databases
---

## `Faker().mariaDB`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/mariadb.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().mariaDB.dataType() // => VARCHAR

    Faker().mariaDB.engine() // => InnoDB
    ```