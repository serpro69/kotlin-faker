---
title: dragonBall
faker: japmedia
---

## `Faker().dragonBall`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/dragon_ball.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().dragonBall.characters() // => Goku
    Faker().dragonBall.races() // => Android
    Faker().dragonBall.planets() // => Alpha
    ```
