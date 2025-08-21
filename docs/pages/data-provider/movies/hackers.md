---
title: hackers
---

## `Faker().hackers`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/hackers.yml:hackers_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().hackers.characters() // => Dade Murphy

    Faker().hackers.handles() // => Zero Cool

    Faker().hackers.quotes() // => Hack the Planet!
    ```
