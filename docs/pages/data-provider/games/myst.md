---
title: myst
faker: games
---

## `Faker().myst`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/myst.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().myst.games() // => Myst

    Faker().myst.creatures() // => squee

    Faker().myst.characters() // => The Stranger

    Faker().myst.ages() // => Myst

    Faker().myst.quotes() // => The ending has not yet been written.
    ```
