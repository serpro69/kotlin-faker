---
title: drWho
faker: tvshows
---

## `Faker().drWho`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/dr_who.yml:dr_who_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().drWho.character() // => Dr. Who

    Faker().drWho.the_doctors() // => First Doctor

    Faker().drWho.actors() // => William Hartnell

    Faker().drWho.catchPhrases() // => Fantastic!

    Faker().drWho.quotes() // => Lots of planets have a north!

    Faker().drWho.villains() // => Helen A

    Faker().drWho.species() // => Time Lord
    ```
