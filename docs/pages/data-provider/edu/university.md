---
title: university
faker: edu
---

## `Faker().university`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/university.yml:university_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().university.prefix() // => The

    Faker().university.suffix() // => University

    Faker().university.name() // => West Carolina University
    ```
