---
title: trainStation
---

## `Faker().trainStation`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/train_station.yml:train_station_provider_dict"
        ```


=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().trainStation.unitedKingdom.metro() // => Aldgate
    Faker().trainStation.unitedKingdom.railway() // => Birmingham New Street railway station

    Faker().trainStation.spain.metro() // => Alto del Arenal
    Faker().trainStation.spain.railway() // => Madrid Atocha

    Faker().trainStation.germany.metro() // => Alexanderplatz
    Faker().trainStation.germany.railway() // => Berlin-Gesundbrunnen station

    Faker().trainStation.unitedStates.metro() // => Back Bay
    Faker().trainStation.unitedStates.railway() // => 30th Street Station
    ```
