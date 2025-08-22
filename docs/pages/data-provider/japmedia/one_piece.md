---
title: onePiece
faker: japmedia
---

## `Faker().onePiece`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/one_piece.yml:one_piece_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().onePiece.characters() // => Monkey D. Luffy

    Faker().onePiece.seas() // => East Blue

    Faker().onePiece.islands() // => Dawn Island

    Faker().onePiece.locations() // => Foosha Village

    Faker().onePiece.quotes() // => I love heroes, but I don't want to be one. Do you even know what a hero is!? For example, you have some meat. Pirates will feast on the meat, but the hero will distribute it among the people! I want to eat the meat!

    Faker().onePiece.akuma_no_mi() // => Gomu Gomu no Mi
    ```
