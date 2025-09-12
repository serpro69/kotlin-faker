---
title: university
faker: edu
---

## `Faker().university`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/university.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().university.prefix() // => The

    Faker().university.suffix() // => University

    Faker().university.name() // => West Carolina University
    ```
