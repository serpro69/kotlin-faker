---
title: bojackHorseman
---

## `Faker().bojackHorseman`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/bojack_horseman.yml:bojack_horseman_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().bojackHorseman.characters() // => Joseph Sugarman

    Faker().bojackHorseman.quotes() // => It gets easier. But you have to do it every day, that's the hard part. But it does get easier

    Faker().bojackHorseman.tongueTwisters() // => Courtney Portnoy portrayed the formerly portly consort in the seaport resort
    ```
