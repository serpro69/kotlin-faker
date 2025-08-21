---
title: mariaDB
---

## `Faker().mariaDB`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/mariadb.yml:mariadb_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().mariaDB.dataType() // => VARCHAR

    Faker().mariaDB.engine() // => InnoDB
    ```