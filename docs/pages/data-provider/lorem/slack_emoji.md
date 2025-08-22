---
title: slackEmoji
faker: lorem
---

## `Faker().slackEmoji`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/slack_emoji.yml:slack_emoji_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().slackEmoji.people() // => :grinning:

    Faker().slackEmoji.nature() // => :seedling:

    Faker().slackEmoji.foodAndDrink() // => :tomato:

    Faker().slackEmoji.celebration() // => :ribbon:

    Faker().slackEmoji.activity() // => :running:

    Faker().slackEmoji.travelAndPlaces() // => :train:

    Faker().slackEmoji.objectsAndSymbols() // => :watch:

    Faker().slackEmoji.custom() // => :beryl:

    // random of the all above
    Faker().slackEmoji.emoji() // => :grin:
    ```
