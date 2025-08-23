---
title: witcher
faker: games
---

## `Faker().witcher`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/witcher.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().witcher.characters() // => Triss Merigold

    Faker().witcher.witchers() // => Geralt of Rivia

    Faker().witcher.schools() // => Wolf

    Faker().witcher.locations() // => Aedd Gynvael

    Faker().witcher.quotes() // => Just five more minutes… Is it 1358 yet? No? Then fuck off!

    Faker().witcher.monsters() // => Archespore

    Faker().witcher.signs() // => Aard

    Faker().witcher.potions() // => Cat

    Faker().witcher.books() // => The Last Wish
    ```
