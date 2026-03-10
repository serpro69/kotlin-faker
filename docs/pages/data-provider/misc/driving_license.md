---
title: drivingLicense
faker: misc
---

## `Faker().drivingLicense`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/driving_license.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    // `#` represents a random digit
    // `?` represents a random letter
    Faker().drivingLicense.license() // => ######
    Faker().drivingLicense.licenseByState("district_of_columbia") // => #######
    ```
