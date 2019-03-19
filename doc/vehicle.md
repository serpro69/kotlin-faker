# `Faker.vehicle`

[Dictionary file](../src/main/resources/locales/en/vehicle.yml)

Available Functions:  
```kotlin
Faker.vehicle.manufacture() // Abarth

// Random manufacturer
Faker.vehicle.makes() // BMW

// Random model by manufacturer
Faker.vehicle.modelsByMake("BMW") // 328i
// or a model from a random manufacturer
Faker.vehicle.modelsByMake("") // A4

Faker.vehicle.colors() // Red

Faker.vehicle.transmissions() // Automanual

Faker.vehicle.driveTypes() // 4x2/2-wheel drive

Faker.vehicle.fuelTypes() // Compressed Natural Gas

Faker.vehicle.styles() // XL

Faker.vehicle.carTypes() // Cargo Van

Faker.vehicle.carOptions() // A/C: Front

Faker.vehicle.standardSpecs() // 1.8L DOHC 16-valve I4 engine -inc: engine cover

Faker.vehicle.doors() // 3

Faker.vehicle.engineSizes() // 6

Faker.vehicle.licensePlate() // ???-#### where '?' is a random upper-case letter, and '#' is a random digit

// Licence plate number by state code
Faker.vehicle.licensePlateByState("AL") // #??#### where '?' is a random upper-case letter, and '#' is a random digit
// or from a random state
Faker.vehicle.licensePlateByState("") // ??? ### where '?' is a random upper-case letter, and '#' is a random digit

Faker.vehicle.cylinderEngine() // Cylinder Engine
```
