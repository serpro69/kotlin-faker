# `Faker.address`

[Dictionary file](../src/main/resources/locales/en/address.yml)

Available Functions:  
```kotlin
Faker.address.country() // => Peru

// Country (fetched by country code)
Faker.address.countryByCode("PE") // => Peru
// or a random one
Faker.address.countryByCode("") // => Ukraine

// Country code (fetched by country name) 
Faker.address.countryByName("Peru") // => PE
// or a random one
Faker.address.countryByName("")

Faker.address.countryCode() // => UA

Faker.address.countryCodeLong() // => URK

Faker.address.buildingNumber() // => 123

Faker.address.community() // => Park Village

Faker.address.secondaryAddress() // => Apt. 123

Faker.address.postcode() // => 12345

// Postcode (fetched by state abbreviation code)
Faker.address.postcodeByState("AL") // => 350XX (where X is a random digit)

Faker.address.state() // => Indiana

Faker.address.stateAbbr() // => NY

Faker.address.timeZone() // => Pacific/Midway

Faker.address.city() // => Bartville

Faker.address.streetName() // => Adams Brook

Faker.address.streetAddress() // => 123 Adams Brook

Faker.address.fullAddress() // => 123 Adams Brook, Bartville, AL 12345-6789

Faker.address.defaultCountry() // => United States of America (this is a static value and only changes with the locale)

```