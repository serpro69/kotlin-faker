---
title: space
---

## `Faker().space`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/space.yml:space_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().space.planet()  // => Mercury

    Faker().space.moon()  // => Moon

    Faker().space.galaxy()  // => Milky Way

    Faker().space.nebula()  // => Lagoon Nebula

    Faker().space.starCluster()  // => Wild Duck

    Faker().space.constellation()  // => Big Dipper

    Faker().space.star()  // => Sun

    Faker().space.agency()  // => National Aeronautics and Space Administration

    Faker().space.agencyAbv()  // => NASA

    Faker().space.nasaSpaceCraft()  // => Orion

    Faker().space.company()  // => Rocket Lab

    Faker().space.distance_measurement()  // => light years

    Faker().space.meteorite()  // => Aarhus

    Faker().space.launchVehicle()  // => Antares
    ```
