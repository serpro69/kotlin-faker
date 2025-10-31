---
title: hitchhikersGuideToTheGalaxy
faker: movies
---

## `Faker().hitchhikersGuideToTheGalaxy`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/hitchhikers_guide_to_the_galaxy.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().hitchhikersGuideToTheGalaxy.characters() // => Agda
    Faker().hitchhikersGuideToTheGalaxy.locations() // => 29 Arlington Avenue
    Faker().hitchhikersGuideToTheGalaxy.marvinQuote() // => Life? Don't talk to me about life.
    Faker().hitchhikersGuideToTheGalaxy.planets() // => Allosimanius Syneca
    Faker().hitchhikersGuideToTheGalaxy.quotes() // => Earth: Mostly Harmless
    Faker().hitchhikersGuideToTheGalaxy.species() // => Algolian Suntiger
    Faker().hitchhikersGuideToTheGalaxy.starships() // => Billion Year Bunker
    ```
