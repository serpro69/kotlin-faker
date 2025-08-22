---
title: swordArtOnline
faker: japmedia
---

## `Faker().swordArtOnline`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/sword_art_online.yml:sword_art_online_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().swordArtOnline.realName() // => Alice Zuberg

    Faker().swordArtOnline.gameName() // => Sinon

    Faker().swordArtOnline.location() // => Aincrad

    Faker().swordArtOnline.item() // => Blackwyrm Coat
    ```
