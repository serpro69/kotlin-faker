---
title: finalSpace
faker: tvshows
---

## `Faker().finalSpace`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/final_space.yml:final_space_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().final_space.characters() // => A.V.A

    Faker().final_space.vehicles() // => Crimson Light

    Faker().final_space.quotes() // => It's an alien on my face! It's an alien on my...It's a space alien!
    ```
