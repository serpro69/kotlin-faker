---
icon: octicons/command-palette-16
---

# :octicons-command-palette-16: Faker CLI

Faker-CLI is a command line interface that helps you to quickly explore the available functionality of kotlin-faker.

## Prerequisites

`faker-bot` is released as a native linux image, and thus there are no additional pre-requisites for running the application.

## Installation

The latest releases are available on [github :fontawesome-brands-github:](https://github.com/serpro69/kotlin-faker/releases/latest)

## Commands

### `list`

This command will print all existing functionality of kotlin-faker.

=== "cli :octicons-command-palette-16:"
    ```shell
    faker-bot list
    ```

=== "output :material-text:"
    ```text
    Faker()
    ├── address
    │   ├── buildingNumber()
    │   ├── city()
    │   ├── cityName()
    │   ├── cityWithState()
    │   ├── community()
    │   ├── country()
    │   ├── countryByCode()
    │   ├── countryByName()
    │   ├── countryCode()
    │   ├── countryCodeLong()
    │   ├── defaultCountry()
    │   ├── fullAddress()
    │   ├── mailbox()
    │   ├── postcode()
    │   ├── postcodeByState()
    │   ├── secondaryAddress()
    │   ├── state()
    │   ├── stateAbbr()
    │   ├── streetAddress()
    │   ├── streetName()
    │   └── timeZone()
    ├── ancient
    │   ├── god()
    │   ├── hero()
    │   ├── primordial()
    │   └── titan()
    ├── animal
    │   └── name()
    ├── app
    │   ├── author()
    │   ├── name()
    │   └── version()
    ├── appliance
    │   ├── brand()
    │   └── equipment()
    ├── aquaTeenHungerForce
    │   ├── character()
    │   └── quote()
    ├── artist
    │   └── names()
    ├── backToTheFuture
    │   ├── characters()
    │   ├── dates()
    │   └── quotes()
    ├── bank
    │   ├── ibanDetails()
    │   ├── name()
    │   └── swiftBic()
    ├── barcode
    │   ├── compositeSymbol()
    │   ├── ean13()
    │   ├── ean8()
    │   ├── isbn()
    │   ├── ismn()
    │   ├── issn()
    │   ├── upcA()
    │   └── upcE()
    ├── ...
    │   ├── ...
    │   └── ...
    └── ...
        └── ...
    ```

### `lookup`

Often a `lookup` command will be more suitable to quickly find a necessary function.

This command will print all the functions that match the specified string argument (case-insensitive)

=== "cli :octicons-command-palette-16:"
    ```shell
    faker-bot lookup QuoTe
    ```

=== "output :material-text:"
    ```text
    Faker()
    ├── aquaTeenHungerForce
    │   └── quote()
    ├── backToTheFuture
    │   └── quotes()
    ├── bigBangTheory
    │   └── quotes()
    ├── bojackHorseman
    │   └── quotes()
    ├── buffy
    │   └── quotes()
    ├── community
    │   └── quotes()
    ├── control
    │   └── quote()
    ├── departed
    │   └── quotes()
    ├── drWho
    │   └── quotes()
    ├── dumbAndDumber
    │   └── quotes()
    ├── dune
    │   └── quotes()
    ├── fallout
    │   └── quotes()
    ├── familyGuy
    │   └── quote()
    ├── freshPriceOfBelAir
    │   └── quotes()
    ├── friends
    │   └── quotes()
    ├── futurama
    │   └── quotes()
    ├── gameOfThrones
    │   └── quotes()
    ├── ghostBusters
    │   └── quotes()
    ├── greekPhilosophers
    │   └── quotes()
    ├── harryPotter
    │   └── quotes()
    ├── heroesOfTheStorm
    │   └── quotes()
    ├── heyArnold
    │   └── quotes()
    ├── hitchhikersGuideToTheGalaxy
    │   ├── marvinQuote()
    │   └── quotes()
    ├── hobbit
    │   └── quote()
    ├── howIMetYourMother
    │   └── quote()
    ├── leagueOfLegends
    │   └── quote()
    ├── lebowski
    │   └── quotes()
    ├── lordOfTheRings
    │   └── quotes()
    ├── michaelScott
    │   └── quotes()
    ├── movie
    │   └── quote()
    ├── myst
    │   └── quotes()
    ├── newGirl
    │   └── quotes()
    ├── onePiece
    │   └── quotes()
    ├── overwatch
    │   └── quotes()
    ├── princessBride
    │   └── quotes()
    ├── rickAndMorty
    │   └── quotes()
    ├── rupaul
    │   └── quotes()
    ├── seinfeld
    │   └── quote()
    ├── siliconValley
    │   └── quotes()
    ├── simpsons
    │   └── quotes()
    ├── southPark
    │   └── quotes()
    ├── starWars
    │   ├── quote()
    │   └── quotes()
    ├── stargate
    │   └── quotes()
    ├── strangerThings
    │   └── quote()
    ├── streetFighter
    │   └── quotes()
    ├── suits
    │   └── quotes()
    ├── theExpanse
    │   └── quotes()
    ├── theITCrowd
    │   └── quotes()
    ├── twinPeaks
    │   └── quotes()
    ├── vForVendetta
    │   └── quotes()
    ├── ventureBros
    │   └── quote()
    ├── warhammerFantasy
    │   └── quotes()
    ├── witcher
    │   └── quotes()
    ├── worldOfWarcraft
    │   └── quotes()
    └── yoda
        └── quotes()
    ```

## Options

### `--verbose`

Each of the commands accepts a `--verbose` option that will also print a randomly generated value.

=== "cli :octicons-command-palette-16:"
    ```shell
    faker-bot lookup ea --verbose
    ```

=== "output :material-text:"
    ```text
    Faker()
    ├── address
    │   └── stateAbbr() // => SD
    ├── barcode
    │   ├── ean13() // => 609208203729
    │   └── ean8() // => 7145993
    ├── basketball
    │   └── teams() // => Dallas Mavericks
    ├── beer
    │   └── yeast() // => 5335 - Lactobacillus
    ├── cannabis
    │   └── healthBenefits() // => stimulates function in the immune system
    ├── dnd
    │   ├── meleeWeapons() // => Greatclub
    │   └── rangedWeapons() // => Crossbow
    ├── dota
    │   └── team() // => Invictus Gaming
    ├── eSport
    │   ├── leagues() // => MLG
    │   └── teams() // => iBUYPOWER
    ├── elderScrolls
    │   └── creature() // => Skeleton
    ├── food
    │   ├── measurementSizes() // => 1/4
    │   ├── measurements() // => pint
    │   └── metricMeasurements() // => deciliter
    ├── football
    │   └── teams() // => Atletico Madrid
    ├── markdown
    │   └── headers() // => ###
    ├── myst
    │   └── creatures() // => karnaks
    ├── onePiece
    │   └── seas() // => South Blue
    ├── programmingLanguage
    │   └── creator() // => James Gosling
    ├── space
    │   └── distanceMeasurement() // => AU
    ├── swordArtOnline
    │   └── realName() // => Viksul Ur Shasta
    ├── warhammerFantasy
    │   └── creatures() // => Hippogriff
    └── worldCup
        └── teams() // => Morocco
    ```

### `--locale`

Combining `--locale` option with `--verbose` will output translated values using the selected locale.

=== "cli :octicons-command-palette-16:"
    ```shell
    lookup names --verbose --locale uk
    ```

=== "output :material-text:"
    ```text
    Faker()
    ├── artist
    │   └── names() // => Сандро Ботічеллі
    ├── greekPhilosophers
    │   └── names() // => Pythagoras
    ├── heroes
    │   └── names() // => Tazar
    ├── heroesOfTheStorm
    │   └── classNames() // => Support
    └── pokemon
        └── names() // => Onix
    ```

!!! info
    Notice how some values are not translated because of a partially-localized [uk](../pages/locales/uk.md) dictionary.
    <br>
    See also [Faker Configuration - Locale](./faker-configuration.md#locale) for more information about generating values in another language.
