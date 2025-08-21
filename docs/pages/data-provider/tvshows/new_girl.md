---
title: newGirl
---

## `Faker().newGirl`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/new_girl.yml:new_girl_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().newGirl.characters() // => Winston Bishop

    Faker().newGirl.quotes() // => A no-nail oath? You thought I was gonna sleep with one of you, like I just couldn't help it?
    ```
