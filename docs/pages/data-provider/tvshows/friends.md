---
title: friends
faker: tvshows
---

## `Faker().friends`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/friends.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().friends.characters() // => Chandler Bing

    Faker().friends.locations() // => Monica's Apartment

    Faker().friends.quotes() // => I can handle this. Handle is my middle name. Actually, handle is the middle of my first name.
    ```
