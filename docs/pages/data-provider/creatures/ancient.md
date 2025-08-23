---
title: ancient
faker: creatures
---

## `Faker().ancient`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/ancient.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().ancient.god() // => Apollo

    Faker().ancient.primordial() // => Chaos

    Faker().ancient.titan() // => Atlas

    Faker().ancient.hero // => Achilles
    ```
