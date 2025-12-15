---
title: space
faker: tvshows
---

## `Faker().space`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/spongebob.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().spongebob.characters() // => SpongeBob SquarePants

    Faker().spongebob.quotes() // => I'm ready! I'm ready!

    Faker().spongebob.episodes() // => Help Wanted
    ```
