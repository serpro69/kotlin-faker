# Agent Instructions for kotlin-faker

This document provides guidelines for AI agents working on this codebase.

## Build, Lint, and Test

- **Build:** `./gradlew build`
- **Run all tests:** `./gradlew test integrationTest`
- **Run a single test class:** `./gradlew test --tests "package.ClassName"`
- **Run a single test method:** `./gradlew test --tests "package.ClassName.methodName"`
- **Lint:** There is no dedicated lint task. Follow existing code style.

## Code Style

- **Formatting:**
  - 4-space indentation.
  - KDoc comments for all public members.
  - Keep imports sorted alphabetically.
- **Naming Conventions:**
  - `PascalCase` for classes and objects.
  - `camelCase` for functions and properties.
- **Types:**
  - Use explicit types for all public properties and function signatures.
- **Error Handling:**
  - Use standard Kotlin `try-catch` blocks for exceptions.
  - `lazy` initialization is common for properties.
- **Nullability:**
  - Avoid nullable types where possible.
