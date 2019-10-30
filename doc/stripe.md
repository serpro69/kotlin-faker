# `Faker().stripe`

[Dictionary file](../src/main/resources/locales/en/stripe.yml)

Available Functions:  
```kotlin
Faker().stripe.validCards("visa") // => 4242424242424242

Faker().stripe.validCards("") // => 4000056655665556

Faker().stripe.validTokens("visa") // => tok_visa

Faker().stripe.validTokens("") // => tok_mastercard

Faker().stripe.invalidCards("addressZipFail") // => 4000000000000010

Faker().stripe.invalidCards("") // => 4000000000000036
```
