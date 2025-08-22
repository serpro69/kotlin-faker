---
title: siliconValley
faker: tvshows
---

## `Faker().siliconValley`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/silicon_valley.yml:silicon_valley_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().siliconValley.characters() // => Richard Hendricks

    Faker().siliconValley.companies() // => Pied Piper

    Faker().siliconValley.quotes() // => I don't want to live in a world where someone else is making the world a better place better than we are.

    Faker().siliconValley.apps() // => Nip Alert

    Faker().siliconValley.inventions() // => Telehuman

    Faker().siliconValley.mottos() // => Cloud-based, disruptive systems

    Faker().siliconValley.urls() // => http://raviga.com

    Faker().siliconValley.email() // => ichard@piedpiper.test
    ```
