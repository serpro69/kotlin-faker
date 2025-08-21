---
title: app
---

## `Faker().app`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/app.yml:app_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().app.name() // => Redhold

    Faker().app.version() // => 0.X.X (where X is a random digit)

    Faker().app.author() // => John Smith
    ```
