---
title: drone
---

## `Faker().drone`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/drone.yml:drone_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    // `#` represents a random digit
    Faker().drone.name() // => DJI Mavic Air 2
    Faker().drone.weight() // => ### g
    Faker().drone.maxAscentSpeed() // => # m/s
    Faker().drone.maxDescentSpeed() // => # m/s
    Faker().drone.flightTime() // => ## min
    Faker().drone.maxAltitude() // =>  #### m
    Faker().drone.maxFlightDistance() // =>  #### m
    Faker().drone.maxSpeed() // => ## m/s
    Faker().drone.maxWindResistance() // => ##.# m/s
    Faker().drone.maxAngularVelocity() // => ##°/s
    Faker().drone.maxTiltAngle() // => ##°
    Faker().drone.operatingTemperature() // => ##°-###°F
    Faker().drone.batteryCapacity() // => 3### mAh
    Faker().drone.batteryVoltage() // => ##.#V
    Faker().drone.batteryType() // => LiPo 4S
    Faker().drone.batteryWeight() // => ### g
    Faker().drone.chargingTemperature() // => ##°-###°F
    Faker().drone.maxChargingPower() // => ##W
    Faker().drone.iso() // => 100-3200
    Faker().drone.maxResolution() // => ##MP
    Faker().drone.photoFormat() // => JPEG
    Faker().drone.videoFormat() // => MP4
    Faker().drone.maxShutterSpeed() // => 60
    Faker().drone.minShutterSpeed() // => 1/8000
    Faker().drone.shutterSpeedUnits() // => s
    ```
