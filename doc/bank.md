# `Faker().bank`

[Dictionary file](../core/src/main/resources/locales/en/bank.yml)

Available Functions:  
```kotlin
Faker().bank.name() // => UBS CLEARING AND EXECUTION SERVICES LIMITED

Faker().bank.swift_bic() // => AACCGB21

// IBAN Code (fetched by country code, case-insensitive)
Faker().bank.iban_details("ua") // => 1234567890123456789012345
// or a random one
Faker().bank.iban_details("") // => 12345678901234
```
