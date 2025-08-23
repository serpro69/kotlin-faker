---
title: airport
faker: travel
---

## `Faker().airport`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/airport.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().airport.unitedStates.large() // => Hartsfield Jackson Atlanta International Airport
    Faker().airport.unitedStates.medium() // => Albuquerque International Sunport
    Faker().airport.unitedStates.small() // => Albany International Airport
    Faker().airport.unitedStates.iataCode.large() // => ATL
    Faker().airport.unitedStates.iataCode.medium() // => ABQ
    Faker().airport.unitedStates.iataCode.small() // => ALB

    Faker().airport.europeanUnion.large() // => Gatwick Airport
    Faker().airport.europeanUnion.medium() // => Birmingham Airport
    Faker().airport.europeanUnion.iataCode.large() // => LGW
    Faker().airport.europeanUnion.iataCode.medium() // => BHX
    ```
