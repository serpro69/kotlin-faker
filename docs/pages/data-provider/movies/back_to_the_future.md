---
title: backToTheFuture
faker: movies
---

## `Faker().backToTheFuture`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/back_to_the_future.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().backToTheFuture.characters() // => Marty McFly

    Faker().backToTheFuture.dates() // => November 5, 1955

    Faker().backToTheFuture.quotes() // => Ah, Jesus Christ! Jesus Christ, Doc, you disintegrated Einstein!
    ```
