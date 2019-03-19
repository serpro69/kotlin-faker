# `Faker.starWars`

[Dictionary file](../src/main/resources/locales/en/star_wars.yml)

Available Functions:  
```kotlin
Faker.starWars.characters() // Padme Amidala
Faker.starWars.callSquadrons() // Rogue
Faker.starWars.callNumbers() // 6
Faker.starWars.callSign() // Blue Leader
Faker.starWars.droids() // 2-1B
Faker.starWars.planets() // Alderaan
Faker.starWars.species() // Ewok
Faker.starWars.vehicles() // V-Wing Fighter
Faker.starWars.wookieeWords() // wyaaaaaa

// Random quote from a character
Faker.starWars.quotes("admiral_ackbar") // "It's a trap!"
// or a random quote of any character
Faker.startWars.quotes("") // It's the ship that made the Kessel Run in less than 12 parsecs.
// or
Faker.startWars.quote() // It's the ship that made the Kessel Run in less than 12 parsecs.

// Alternative spelling of a character name
Faker.starWars.alternateCharacterSpellings("admiral_ackbar") // akbar
// or of any random character
Faker.starWars.alternateCharacterSpellings() // 'anakin'
```
