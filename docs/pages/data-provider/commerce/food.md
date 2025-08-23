---
title: food
faker: commerce
---

## `Faker().food`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/food.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().food.allergens() // Eggs

    Faker().food.dish() // Arepas

    Faker().food.descriptions() // Three eggs with cilantro, tomatoes, onions, avocados and melted Emmental cheese. With a side of roasted potatoes, and your choice of toast or croissant.

    Faker().food.ingredients() // Achacha

    Faker().food.fruits() // Apples

    Faker().food.vegetables() // Artichoke

    Faker().food.spices() // Achiote Seed

    Faker().food.measurements() // teaspoon

    Faker().food.measurementSizes() // 1/4

    Faker().food.metricMeasurements() // milliliter

    Faker().food.sushi() // Abalone

    Faker().food.ethnicCategory // => Peruvian
    ```
