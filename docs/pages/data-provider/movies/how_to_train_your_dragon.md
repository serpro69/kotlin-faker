---
title: howToTrainYourDragon
faker: movies
---

## `Faker().howToTrainYourDragon`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/how_to_train_your_dragon.yml:how_to_train_your_dragon_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().howToTrainYourDragon.characters() // => Agnar

    Faker().howToTrainYourDragon.dragons() // => Axewing

    Faker().howToTrainYourDragon.locations() // => Algae Island
    ```
