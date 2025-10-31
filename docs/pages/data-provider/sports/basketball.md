---
title: basketball
faker: sports
---

## `Faker().basketball`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/basketball.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    ---kotlin
    Faker().basketball.teams() // => Los Angeles Lakers

    Faker().basketball.players() // => LeBron James

    Faker().basketball.coaches() // => Doc Rivers

    Faker().basketball.positions() // => Power Forward
    ```
