# Changelog
All notable changes to this project will be documented in this file.

The format follows [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and the project versioning adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html) 

## [1.9.0] - UNRELEASED
### Added
- [#96](https://github.com/serpro69/kotlin-faker/issues/96) [core] Add `randomSubset` and `randomSublist` to `RandomService`
- [#92](https://github.com/serpro69/kotlin-faker/issues/92) [core] Add `randomString` function to `RandomService`
- [#86](https://github.com/serpro69/kotlin-faker/issues/86) [core] Generate birth-date based on the age

### Changed
- [#97](https://github.com/serpro69/kotlin-faker/issues/97) [core] Change `RandomService#nextString` to generate strings only within given locale
- [#100](https://github.com/serpro69/kotlin-faker/issues/100) [core] Add deprecation warning for `RandomService#nextString` since it's going to be replaced with `RandomService#randomString`

## [1.8.0] - 2021-10-03
### Added
- [#67](https://github.com/serpro69/kotlin-faker/issues/67) [core] Access to `RandomService` through `Faker` for generating random `Int`, `Double`, `Float`, etc.
- [#77](https://github.com/serpro69/kotlin-faker/pull/77) [core] Extra functionality to `RandomService` - `nextEnum()`, `nextUUID()`, `nextLong(bound)` functions.
- [#69](https://github.com/serpro69/kotlin-faker/pull/69) [core] Passing `seed` directly to `FakerConfig` instead of through `java.util.Random` instance
- [#71](https://github.com/serpro69/kotlin-faker/pull/71) [core] DSL for creating and configuring `Faker`
- [#78](https://github.com/serpro69/kotlin-faker/pull/78) [core] Support sealed classes in `RandomProvider#randomClassInstance` fun
- [#88](https://github.com/serpro69/kotlin-faker/pull/88) [core] Postpone initialization of FakerConfig through the Builder

### Changed
- Configurable `length` of the string generated with `RandomService#nextString`

### Fixed
- [#65](https://github.com/serpro69/kotlin-faker/issues/65) [core] Could not initialize class `io.github.serpro69.kfaker.Mapper` with SpringBoot `2.4.x`
- [#60](https://github.com/serpro69/kotlin-faker/issues/60) [core] Move out of Bintray/Jcenter
- [#79](https://github.com/serpro69/kotlin-faker/issues/79) [core] java.lang.NoClassDefFoundError: org/yaml/snakeyaml/error/YAMLException
- [#81](https://github.com/serpro69/kotlin-faker/issues/81) [core] `RandomProvider#randomClassInstance` fails for object types
- [#90](https://github.com/serpro69/kotlin-faker/pull/90) [core] Android `java.lang.NoClassDefFoundError: FakerService$$ExternalSyntheticLambda1`
- [#87](https://github.com/serpro69/kotlin-faker/pull/87) [core] Parameter 'city_root' not found in 'address' category
- [#89](https://github.com/serpro69/kotlin-faker/pull/89) [core] Parameter 'male_last_name' not found in 'name' category for "ru" locale

## [1.7.1] - 2021-04-28
### Fixed
- [#45](https://github.com/serpro69/kotlin-faker/pull/45) [core] Parameter 'city_name' not found in 'address'

## [1.7.0] - 2021-04-16
### Added
- [#59](https://github.com/serpro69/kotlin-faker/pull/59) [core] Random money amount
- [#62](https://github.com/serpro69/kotlin-faker/pull/62) [core] Add nullable types to random provider type generator

## [1.6.0] - 2020-12-30
### Added
- [#44](https://github.com/serpro69/kotlin-faker/pull/44) [core] Add support for random instance configuration.
- [#47](https://github.com/serpro69/kotlin-faker/issues/47) [core] Publish release candidates to bintray
- [#49](https://github.com/serpro69/kotlin-faker/issues/49) [core] Unique values exclusions with wildcards
- [#46](https://github.com/serpro69/kotlin-faker/issues/46) [core] Support deterministic constructor selection for randomClassInstance

### Fixed
- [#26](https://github.com/serpro69/kotlin-faker/issues/26) [core] Parameter '4' not found in 'vehicle' category
- [#48](https://github.com/serpro69/kotlin-faker/issues/48) [core] streetFighter#moves: class java.util.LinkedHashMap cannot be cast to class java.lang.String
- [#50](https://github.com/serpro69/kotlin-faker/issues/50) [core] Horseman spelt wrong
- [#56](https://github.com/serpro69/kotlin-faker/issues/56) [core] Values with single '?' char are not always letterified

### Changed
- [core] Configuration for generation of unique values. Old functionality is deprecated and will be removed in future releases. This relates to changes in [#49](https://github.com/serpro69/kotlin-faker/issues/49)

## [1.5.0] - 2020-08-30
### Added
- [#40](https://github.com/serpro69/kotlin-faker/issues/40) [core] Add enum support for `RandomProvider`
- [#39](https://github.com/serpro69/kotlin-faker/issues/39) [core] Update dict files.
  - Including new functions in existing providers:
    - `aquaTeenHungerForce.quote()`
    - `dnd.cities()`
    - `dnd.languages()`
    - `dnd.meleeWeapons()`
    - `dnd.monsters()`
    - `dnd.races()` - replaces deprecated `species()` function.
    - `dnd.rangedWeapons()`
    - `heroesOfTheStorm.classNames()` - replaces deprecated `classes()` function
    - `movie.title()`
    - `name.neutralFirstName()`
    - `phish.albums()`
    - `phish.musicians()`
    - `phish.songs()` - replaces deprecated `song()` function
    - `simpsons.episodeTitles()`
  - Including new `faker` providers:
    - `barcode`
    - `bigBangTheory`
    - `drivingLicense`
    - `drone`
    - `futurama`
    - `minecraft`
    - `prince`
    - `rush`
    - `streetFighter`
    
### Changed
- [#32](https://github.com/serpro69/kotlin-faker/issues/32) Upgrade kotlin to 1.4.0

## [1.4.1] - 2020-08-22
### Added
- [#41](https://github.com/serpro69/kotlin-faker/issues/41) publish to maven central

## [1.4.0] - 2020-07-09
### Fixed
- [#36](https://github.com/serpro69/kotlin-faker/issues/36) Build native-image before uploading to bintray

### Changed
- [#37](https://github.com/serpro69/kotlin-faker/issues/37) Revisit automated builds for patches

### Added
- [#34](https://github.com/serpro69/kotlin-faker/issues/34) [core] 8 new providers:
  - `warhammerFantasy`
  - `suits`
  - `show`
  - `pearlJam`
  - `departed`
  - `control`
  - `dnd`
  - `blood`
- [#33](https://github.com/serpro69/kotlin-faker/issues/33) [faker-bot] partial matching for provider names

## [1.3.1] - 2020-06-07
### Fixed
- [#27](https://github.com/serpro69/kotlin-faker/issues/27) Resolving partially-localized provider functions
with secondary_key

## [1.3.0] - 2020-06-03
### Added
- [#24](https://github.com/serpro69/kotlin-faker/issues/24) faker-bot cli application
- Automated releases to github

### Changed
- [#29](https://github.com/serpro69/kotlin-faker/issues/29) Remove classgraph dependency
- Split core faker functionality and cli bot application into sub-projects.

## [1.2.0] - 2020-05-17
### Added
- 3 new providers: `chiquito`, `computer`, and `rajnikanth`
- New functions to existing providers:
    - `address.cityWithState()`
    - `address.mailbox()`
    - `gender.shortBinaryTypes()`
- `educator` provider changed completely due to new dict file structure
- Upgrades to existing dict files
- Automated versioning (patches only) and deploys

### Fixed
- [#18](https://github.com/serpro69/kotlin-faker/issues/18) Visibility of `randomClassInstance()` function 
in [RandomProvider](core/src/main/kotlin/io/github/serpro69/kfaker/provider/RandomProvider.kt) class
- [#20](https://github.com/serpro69/kotlin-faker/issues/20) Issues with FasterXML Jackson 2.10.1

## [1.1.0] - 2019-11-30
### Added
- 3 new providers: `game`, `horse`, and `opera` 
- 2 new locales: `th` and `en-TH`
- New functions to existing providers:
    - `cannabis.brands()`
    - `company.sicCode()`
    - `internet.email(name)`
    - `internet.safeEmail(name)`
    
### Changed
- Rename functions as per changes in the dictionary files:
    - `drWho.villians()` -> `drWho.villains()`
    - `space.launchVehicule()` -> `space.launchVehicle()`
- Updated all dictionary files incl. localizations

### Fixed
- [#15](https://github.com/serpro69/kotlin-faker/issues/15) Sources artifact is empty


## [1.0.0] - 2019-10-30 
### Added
- `FakerConfig` for configuration of `Faker` instance
- [#7](https://github.com/serpro69/kotlin-faker/issues/7) Generation of unique values through `Faker` instance and separate providers
- [#8](https://github.com/serpro69/kotlin-faker/issues/8) Exclusion of generated values for global unique generator
- [#12](https://github.com/serpro69/kotlin-faker/issues/12) Generation of email addresses to `Internet` provider

### Changed
- Make `Faker` a class instead of singleton object
- [#13](https://github.com/serpro69/kotlin-faker/issues/13) Rename `Internet.safeEmail` to `Internet.domain`

## [0.2.0] - 2019-09-30 
### Added
- [#1](https://github.com/serpro69/kotlin-faker/issues/1) Random class instance generator
- [#2](https://github.com/serpro69/kotlin-faker/issues/2) Support for deterministic random

## [0.1.0] - 2019-04-16 
### Added
- Generator of fake data for the majority of .yml files
- Readme containing installation and usage examples
- This changelog file
- CI through travis
- Publishing to bintray

### Fixed
- [#3](https://github.com/serpro69/kotlin-faker/issues/3) Initializing faker with invalid locale
- [#4](https://github.com/serpro69/kotlin-faker/issues/4) Resolving "separator" category
- [#5](https://github.com/serpro69/kotlin-faker/issues/5) Reading .yml files from compiled .jar
