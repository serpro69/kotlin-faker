---
title: naruto
faker: japmedia
---

## `Faker().naruto`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/naruto.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().naruto.characters() // => "Naruto Uzumaki"

    Faker().naruto.villages() // => "Konohagakure (Leaf Village)"

    Faker().naruto.eyes() // => "Byakugan"

    Faker().naruto.demons() // => "One-Tails (Shukaku)"
    ```
