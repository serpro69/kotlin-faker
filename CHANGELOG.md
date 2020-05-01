# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html) 
with the exception of using "patch" version numbers only when necessary. 
<i>For example there could be a released version `1.0` (without the patch number), 
but if a hotfix is needed for this version and no new functionality is added, 
then the next version would be `1.0.1`</i>

## [1.2] - UNRELEASED

## [1.1.2] - 2020-05-01
### Fixed
- [#20](https://github.com/serpro69/kotlin-faker/issues/20) Issues with FasterXML Jackson 2.10.1

## [1.1.1] - 2020-01-28
### Fixed
- [#18](https://github.com/serpro69/kotlin-faker/issues/18) Visibility of `randomClassInstance()` function 
in [RandomProvider](src/main/kotlin/io/github/serpro69/kfaker/provider/RandomProvider.kt) class

## [1.1] - 2019-11-30
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


## [1.0] - 2019-10-30 
### Added
- `FakerConfig` for configuration of `Faker` instance
- [#7](https://github.com/serpro69/kotlin-faker/issues/7) Generation of unique values through `Faker` instance and separate providers
- [#8](https://github.com/serpro69/kotlin-faker/issues/8) Exclusion of generated values for global unique generator
- [#12](https://github.com/serpro69/kotlin-faker/issues/12) Generation of email addresses to `Internet` provider

### Changed
- Make `Faker` a class instead of singleton object
- [#13](https://github.com/serpro69/kotlin-faker/issues/13) Rename `Internet.safeEmail` to `Internet.domain`

## [0.2] - 2019-09-30 
### Added
- [#1](https://github.com/serpro69/kotlin-faker/issues/1) Random class instance generator
- [#2](https://github.com/serpro69/kotlin-faker/issues/2) Support for deterministic random

## [0.1] - 2019-04-16 
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
