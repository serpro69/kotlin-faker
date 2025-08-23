---
title: movie
faker: movies
---

## `Faker().movie`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/movie.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().movie.title() // => 12 Angry Men
    Faker().movie.quote() // => Frankly, my dear, I don’t give a damn.
    ```
