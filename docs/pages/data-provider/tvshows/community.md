---
title: community
faker: tvshows
---

## `Faker().community`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/community.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().community.characters() // => Jeff Winger

    Faker().community.quotes() // => Harrison Ford is irradiating our testicles with microwave satellite transmissions!
    ```
