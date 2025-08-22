---
title: strangerThings
faker: tvshows
---

## `Faker().strangerThings`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/stranger_things.yml:stranger_things_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().strangerThings.character() // => Joyce

    Faker().strangerThings.quote() // => I just didn’t want you to think I was such a wastoid, you know?
    ```
