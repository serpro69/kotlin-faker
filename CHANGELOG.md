# Changelog
All notable changes to this project will be documented in this file.

The format follows [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and the project versioning adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html) 

## [v1.4.0] - UNRELEASED
### Added
- 8 new providers:
  - `warhammerFantasy`
  - `suits`
  - `show`
  - `pearlJam`
  - `departed`
  - `control`
  - `dnd`
  - `blood`

## [v1.3.1] - 2020-06-07
### Fixed
- [#27](https://github.com/serpro69/kotlin-faker/issues/27) Resolving partially-localized provider functions
with secondary_key

## [v1.3.0] - 2020-06-03
### Added
- [#24](https://github.com/serpro69/kotlin-faker/issues/24) faker-bot cli application
- Automated releases to github

### Changed
- [#29](https://github.com/serpro69/kotlin-faker/issues/29) Remove classgraph dependency
- Split core faker functionality and cli bot application into sub-projects.

## [v1.2.0] - 2020-05-17
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
in [RandomProvider](src/main/kotlin/io/github/serpro69/kfaker/provider/RandomProvider.kt) class
- [#20](https://github.com/serpro69/kotlin-faker/issues/20) Issues with FasterXML Jackson 2.10.1

## [v1.1.0] - 2019-11-30
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


## [v1.0.0] - 2019-10-30 
### Added
- `FakerConfig` for configuration of `Faker` instance
- [#7](https://github.com/serpro69/kotlin-faker/issues/7) Generation of unique values through `Faker` instance and separate providers
- [#8](https://github.com/serpro69/kotlin-faker/issues/8) Exclusion of generated values for global unique generator
- [#12](https://github.com/serpro69/kotlin-faker/issues/12) Generation of email addresses to `Internet` provider

### Changed
- Make `Faker` a class instead of singleton object
- [#13](https://github.com/serpro69/kotlin-faker/issues/13) Rename `Internet.safeEmail` to `Internet.domain`

## [v0.2.0] - 2019-09-30 
### Added
- [#1](https://github.com/serpro69/kotlin-faker/issues/1) Random class instance generator
- [#2](https://github.com/serpro69/kotlin-faker/issues/2) Support for deterministic random

## [v0.1.0] - 2019-04-16 
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
