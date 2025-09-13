---
title: newGirl
faker: tvshows
---

## `Faker().newGirl`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/new_girl.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().newGirl.characters() // => Winston Bishop

    Faker().newGirl.quotes() // => A no-nail oath? You thought I was gonna sleep with one of you, like I just couldn't help it?
    ```
