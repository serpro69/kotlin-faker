---
title: quote
faker: lorem
---

## `Faker().quote`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/quote.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().quote.famousLastWords() // I don't know.

    Faker().quote.matz() // I believe consistency and orthogonality are tools of desig

    Faker().quote.mostInterestingManInTheWorld() // His only regret is not knowing what regret feels like.

    Faker().quote.robin() // Holy Agility

    Faker().quote.singularSiegler() // Texas!

    Faker().quote.yoda() // Use your feelings, Obi-Wan, and find him you will.

    Faker().quote.fortuneCookie() // => This cookie senses that you are superstitious; it is an inclination that is bad for your mental health.
    ```
