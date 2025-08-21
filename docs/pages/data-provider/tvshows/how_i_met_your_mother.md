---
title: howIMetYourMother
---

## `Faker().howIMetYourMother`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/how_i_met_your_mother.yml:how_i_met_your_mother_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().howIMetYourMother.character() // => Ted Mosby

    Faker().howIMetYourMother.catchPhrase() // => Legendary

    Faker().howIMetYourMother.highFive() // => Arthritis Five

    Faker().howIMetYourMother.quote() // => Whenever I’m sad, I stop being sad and be awesome instead.
    ```
