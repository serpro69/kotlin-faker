# `Faker.dune`

[Dictionary file](../src/main/resources/locales/en/dune.yml)

Available Functions:  
```kotlin
Faker.dune.characters() // Paul \"Muad'Dib\" \"Usul\" Atreides
Faker.dune.titles() // Master of Assassins
Faker.dune.planets() // Arrakis

// Random quote from a character
Faker.dune.quotes("guild_navigator") // The spice must flow
// or a random one
Faker.dune.quotes("") // They tried and failed, all of them?

// Random saying from a character
Faker.dune.sayings("bene_gesserit") I must not fear. Fear is the mind-killer. Fear is the little-death that brings total obliteration. I will face my fear. I will permit it to pass over me and through me. And when it has gone past I will turn the inner eye to see its path. Where the fear has gone there will be nothing. Only I will remain.
// or a random one
Faker.dune.sayings("") I must not fear. Fear is the mind-killer. Fear is the little-death that brings total obliteration. I will face my fear. I will permit it to pass over me and through me. And when it has gone past I will turn the inner eye to see its path. Where the fear has gone there will be nothing. Only I will remain.

```
