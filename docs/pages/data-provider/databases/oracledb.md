---
title: oracleDB
faker: databases
---

## `Faker().oracleDB`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/oracledb.yml:oracledb_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().oracleDB.dataType() // => VARCHAR
    ```