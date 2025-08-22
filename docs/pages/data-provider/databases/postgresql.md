---
title: postgreSQL
faker: databases
---

## `Faker().postgreSQL`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/postgresql.yml:postgresql_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().postgreSQL.dataType() // => VARCHAR

    Faker().postgreSQL.collation() // => en_US.UTF-8
    ```