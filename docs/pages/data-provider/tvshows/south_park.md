---
title: southPark
faker: tvshows
---

## `Faker().southPark`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/south_park.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().southPark.characters() // => Sharon Marsh

    Faker().southPark.quotes() // => Hippies. They're everywhere. They wanna save Earth, but all they do is smoke pot and smell bad
    ```
