---
title: greekPhilosophers
faker: misc
---

## `Faker().greekPhilosophers`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/greek_philosophers.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().greekPhilosophers.names() // => Plato

    Faker().greekPhilosophers.quotes() // => Quality is not an act, it is a habit.
    ```
