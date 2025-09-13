---
title: ghostBusters
faker: movies
---

## `Faker().ghostBusters`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/ghostbusters.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().ghostBusters.actors() // => Bill Murray

    Faker().ghostBusters.characters() // => Dr. Peter Venkman

    Faker().ghostBusters.quotes() // => This city is headed for a disaster of biblical proportions.
    ```
