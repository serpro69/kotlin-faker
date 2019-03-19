# `Faker.address`

[Dictionary file](../src/main/resources/locales/en/address.yml)

Available Functions:  
```kotlin
// Country
Faker.address.city() // => Peru

// Country (fetched by country code)
Faker.address.countryByCode("PE") // => Peru
// or a random one
Faker.address.countryByCode("") // => Ukraine

// Country code (fetched by country name) 
Faker.address.countryByName("Peru") // => PE
// or a random one
Faker.address.countryByName("")

// Building number
Faker.address.buildingNumber() // => XXX || XXXX || XXXXX (where X is a random digit)

// Community 
Faker.address.community() // => Park Village

// Postcode
Faker.address.postcode() // => XXXXX || XXXXX-XXXX (where X is a random digit)

// Postcode (fetched by state abbreviation code)
Faker.address.postcodeByState("AL") // => 350XX (where X is a random digit)

// State
Faker.address.state() // => Indiana

// State abbreviation code
Faker.address.stateAbbr() // => NY

// Timezone
Faker.address.timeZone() // => Pacific/Midway

// City
Faker.address.city() // => 

// Street name
Faker.address.streetName() // => 

// Street address
Faker.address.streetAddress() // =>

// Full address
Faker.address.fullAddress() // =>

// Default country 
Faker.address.defaultCountry() // => United States of America (this is a static value and only changes with the locale)

```