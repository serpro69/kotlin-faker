---
title: kamenRider
---

## `Faker().kamenRider`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/kamen_rider.yml:kamen_rider_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().kamenRider.showa.series() // => Kamen Rider
    Faker().kamenRider.showa.kamenRiders() // => Kamen Rider 1
    Faker().kamenRider.showa.users() // => Takeshi Hongo
    Faker().kamenRider.showa.transformationDevices() // => Typhoon

    Faker().kamenRider.heisei.series() // => Kamen Rider Kuuga
    Faker().kamenRider.heisei.kamenRiders() // => Kamen Rider Kuuga
    Faker().kamenRider.heisei.users() // => Yusuke Godai
    Faker().kamenRider.heisei.collectibleDevices() // => Advent Card
    Faker().kamenRider.heisei.transformationDevices() // => Acceldriver

    Faker().kamenRider.reiwa.series() // => Kamen Rider Zero-One
    Faker().kamenRider.reiwa.kamenRiders() // => Kamen Rider Zero-One
    Faker().kamenRider.reiwa.users() // => Aruto Hiden
    Faker().kamenRider.reiwa.collectibleDevices() // => Progrisekey
    Faker().kamenRider.reiwa.transformationDevices() // => A.I.M.S. Shotriser
    ```
