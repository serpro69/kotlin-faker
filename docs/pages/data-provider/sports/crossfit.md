---
title: crossfit
---

## `Faker().crossfit`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/crossfit.yml:crossfit_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().crossfit.competitions() // => The Granite Games, Wodapalooza
    Faker().crossfit.maleAthletes() // => Chandler Smith, Mat Fraser
    Faker().crossfit.femaleAthletes() // => Brooke Wells, Tia-Clair Toomey
    Faker().crossfit.movements() // => Handstand Push-up, Rope Climb
    Faker().crossfit.girlWorkouts() // => Fran, Grace
    Faker().crossfit.heroWorkouts() // => DT, Murph
    ```
