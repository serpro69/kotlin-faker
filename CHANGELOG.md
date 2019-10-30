# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0] - 2019-10.30
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
