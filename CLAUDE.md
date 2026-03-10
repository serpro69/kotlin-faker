# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Kotlin-faker is a comprehensive fake data generation library for the JVM, supporting Kotlin, Java, Android, and other JVM languages. It's a Kotlin port of the Ruby faker gem, providing realistic-looking fake data across various domains (names, addresses, books, games, etc.).

## Build Commands

### Standard Development

```bash
# Run unit tests
./gradlew clean test

# Run integration tests
./gradlew clean integrationTest

# Run code formatting checks
./gradlew spotlessCheck

# Full build with all checks
./gradlew clean test integrationTest spotlessCheck build shadowJar

# Build native CLI image (requires GraalVM JDK 17)
./gradlew nativeCompile
```

### Single Test Execution

```bash
# Run a specific test class
./gradlew test --tests "ClassName"

# Run a specific test method
./gradlew test --tests "ClassName.testMethodName"
```

### Documentation

```bash
# Serve documentation locally
mkdocs serve

# Generate API documentation
./gradlew dokkaGfmMultiModule
```

## Architecture

### Module Structure

The project follows a multi-module Gradle build:

- **core**: Core faker functionality with the main `Faker` class and common data providers (names, addresses, internet, etc.)
- **faker/**: Domain-specific faker submodules (books, commerce, creatures, databases, edu, games, humor, japmedia, lorem, misc, movies, music, pictures, sports, tech, travel, tvshows)
- **extension/**: Third-party testing library extensions (blns, kotest-property)
- **cli-bot**: Command-line application for quick faker function lookup (built as GraalVM native image)
- **bom**: Bill-of-Materials for dependency management
- **test**: Helper utilities for integration testing
- **buildSrc/**: Custom Gradle convention plugins that standardize build configuration across modules

### Data Generation Architecture

1. **Dictionary Files**: Fake data definitions live in YAML files under `core/src/main/resources/locales/{locale}/`
2. **Data Providers**: Classes extending `YamlFakeDataProvider` expose data generation functions (e.g., `Name`, `Address`)
3. **Faker Entry Point**: The `Faker` class (or domain-specific faker classes) provides access to all data providers
4. **YAML Processing**: The custom `yaml-to-json` Gradle plugin converts YAML dictionaries to JSON at build time
5. **FakerService**: Central service that loads dictionaries and resolves data values with support for locales and expressions

### Key Patterns

- **Provider Pattern**: Each data category (Name, Address, etc.) is implemented as a provider class with functions that map to dictionary keys
- **Localization**: Dictionary files organized by locale (`en/`, `uk.yml`, etc.) with the default being `en`
- **Category-based Organization**: Dictionary structure follows `{locale}.faker.{category}.{function_name}` hierarchy
- **Unique Data Generation**: Providers support `.unique` for generating non-repeating values

## Adding New Data Providers

When adding a new provider to `core` or creating a new faker module:

1. Add YAML dictionary file to `core/src/main/resources/locales/en/{category}.yml` (or to appropriate faker module)
2. Create provider class extending `YamlFakeDataProvider` in `core/src/main/kotlin/io/github/serpro69/kfaker/provider/` (or faker module's provider package)
3. Add property to `Faker` class (or appropriate faker class) that instantiates the provider
4. Update `cli-bot/src/main/kotlin/io/github/serpro69/kfaker/app/Constants.kt` for CLI support
5. Update native-image `reflect-config.json` in `cli-bot/src/main/resources/META-INF/native-image/`
6. Update test constants in `core/src/test/kotlin/io/github/serpro69/kfaker/TestConstants.kt`
7. Update `cli-bot/src/test/kotlin/io/github/serpro69/kfaker/app/cli/IntrospectorTest.kt`

See CONTRIBUTING.md for detailed step-by-step instructions and examples.

## Java Version Requirements

- **Runtime**: Java 8 + Kotlin 1.9.x
- **Build**: Requires JDK 11 or higher (due to GraalVM buildtools plugin in cli-bot)
- **Toolchains**: Core libs use Gradle toolchains to compile with Java 8 compatibility
- **Native Image**: Requires GraalVM CE JDK 17 for building cli-bot native image

The project uses Gradle toolchains to automatically provision correct JDK versions.

## Code Style

- Follow official Kotlin code style as defined by JetBrains
- Spotless is configured to enforce formatting: `./gradlew spotlessCheck`
- Apply formatting fixes: `./gradlew spotlessApply`

## Testing Requirements

- 100% test coverage for new code (run `./gradlew clean test` for coverage check)
- Integration tests for larger changes and new features (`./gradlew integrationTest`)
- Most providers are covered by dynamic integration tests that use reflection to call all public provider functions

## Publishing

Versioning uses semantic versioning via gradle plugin. Use Makefile targets for releases:

```bash
# Snapshot releases
make snapshot-minor

# Pre-releases
make pre-release-major|minor|patch
make next-pre-release

# Full releases
make release-major|minor|patch
make promote-to-release
```

Or use `./gradlew tag` task with appropriate flags (see README.md "Build and Deploy" section).
