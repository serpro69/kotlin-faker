---
title: programmingLanguage
faker: tech
---

## `Faker().programmingLanguage`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/programming_language.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().programmingLanguage.name() // A# .NET

    Faker().programmingLanguage.creator() // John Backus
    ```
