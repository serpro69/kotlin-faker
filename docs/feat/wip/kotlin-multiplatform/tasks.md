# Tasks: Kotlin Multiplatform support

> Design: [design.md](design.md)
> Implementation: [implementation.md](implementation.md)
> Verification: [verification.md](verification.md)
> Issue: [#226](https://github.com/serpro69/kotlin-faker/issues/226)
> Status: pending
> Created: 2026-11-23
> Release: next major
> Not Doing: desktop Native/Wasm, portable class construction/images, locale splitting, remote dictionaries, full regex parity, npm/Swift SDKs, new concurrency/crypto guarantees, cross-release seeded stability

Tasks 1–4 are the feasibility milestone. The remaining tasks are conditional on its evidence; producing this list does not mark the prototype or migration complete. Sizes exclude mechanical source moves and repeated provider/test registrations. Unexpected bespoke behavior must be split into another small vertical slice before execution.

## Task 1: Inventory inputs and select the trial toolchain

- **Status:** pending
- **Depends on:** —
- **Size:** M
- **Can run in parallel with:** —
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-1](implementation.md#task-1)

### Subtasks

- [ ] 1.1 Inventory `core/src/jvmMain/resources/locales` shapes, fallback keys and regex inputs at actual provider call sites; account for all files and preprocessing.
- [ ] 1.2 Verify Kotlin/Gradle/Xcode, serialization and datetime compatibility; record a tested trial tuple and needed plugin changes in `feasibility.md`.
- [ ] 1.3 Inventory Java types, ICU locale acceptance, uniqueness exclusions and existing checks/coverage instrumentation; assign explicit decisions for gaps.
- [ ] 1.4 Compile a minimal version/API probe and record commands, results and SDK limitations before dependent work.

## Task 2: Compile all dictionaries and generate seeded names

- **Status:** pending
- **Depends on:** Task 1
- **Size:** M
- **Can run in parallel with:** —
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-2](implementation.md#task-2)

### Subtasks

- [ ] 2.1 Create `test/fixtures/kmp-feasibility/` as a standalone build using real YAML inputs and bounded generated common data.
- [ ] 2.2 Implement a lazy name-generation path and controlled seed/sampling candidate without production API changes.
- [ ] 2.3 Compile and access the complete payload on JVM/JS/iOS, including Japanese address data; verify resource-key coverage and RNG-free loading.
- [ ] 2.4 Compare numeric/name vectors and record artifact, compilation, initialization and decoded-data measurements in `feasibility.md`.

## Task 3: Prove addresses, a domain path, uniqueness and regex generation

- **Status:** pending
- **Depends on:** Task 2
- **Size:** M
- **Can run in parallel with:** —
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-3](implementation.md#task-3)

### Subtasks

- [ ] 3.1 Add real address fallback/cross-category behavior and a book-author path to the fixture.
- [ ] 3.2 Add explicit unique factories and tests for interleaving, clearing and exhaustion.
- [ ] 3.3 Implement and document the corpus-driven regex candidate; reject unsupported syntax before RNG consumption.
- [ ] 3.4 Validate the pattern corpus and run semantic plus seeded cases on all targets, recording unresolved constructs as blockers.

## Task 4: Consume published candidate artifacts and close feasibility gates

- **Status:** pending
- **Depends on:** Task 3
- **Size:** M
- **Can run in parallel with:** —
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-4](implementation.md#task-4)

### Subtasks

- [ ] 4.1 Publish candidate core/domain coordinates into a project-local repository and consume them from an independent KMP build.
- [ ] 4.2 Run JS browser/Node and iOS simulator paths, and verify the root-POM JVM fallback separately from Gradle metadata resolution.
- [ ] 4.3 Complete `feasibility.md` with full-data costs, supported grammar, seeded sampling rules, toolchain evidence and the selected representation.
- [ ] 4.4 Resolve every hard feasibility failure or revise the design explicitly before production migration; do not trade away locales or seeded equality.

## Task 5: Integrate common dictionary resolution into production

- **Status:** pending
- **Depends on:** Task 4
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-5](implementation.md#task-5)

### Subtasks

- [ ] 5.1 Refactor `yaml-to-json.gradle.kts` and `core/build.gradle.kts` to generate reproducible common sources from the existing YAML paths.
- [ ] 5.2 Add common decoding/resource lookup and adapt `FakerService`, `Mapper` and dictionary types to use it through the still-buildable JVM facade.
- [ ] 5.3 Preserve base/exact locale overlays, category aliases, nested expressions and phone/currency/separator special cases.
- [ ] 5.4 Verify focused production resolver cases and full-corpus packaging against the proven fixture, including explicit missing-data errors.

## Task 6: Integrate controlled seeded primitive operations

- **Status:** pending
- **Depends on:** Task 5
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-6](implementation.md#task-6)

### Subtasks

- [ ] 6.1 Add the approved common seeded stream/sampling implementation and refactor `FakerConfig`/`AbstractFaker` to create independent per-Faker state.
- [ ] 6.2 Route primitive operations in `RandomService`, `IRandom` and `RandomProvider` through the controlled pipeline.
- [ ] 6.3 Audit direct random/collection calls and document injected-source, sequential-call and collection-order conditions.
- [ ] 6.4 Verify boundary and mixed-operation vectors, independent instances and cache independence across targets.

## Task 7: Port uniqueness and settle exclusion semantics

- **Status:** pending
- **Depends on:** Task 6
- **Size:** M
- **Can run in parallel with:** Task 9
- **Docs:** [implementation.md#task-7](implementation.md#task-7)

### Subtasks

- [ ] 7.1 Replace reflective cloning in `LocalUniqueDataProvider` with typed factories, including additional dependencies for Internet, Dune and Finance.
- [ ] 7.2 Replace property return-type inspection in global configuration with explicit common identities, retaining provider/function scopes and local precedence.
- [ ] 7.3 Specify deterministic exclusion matching/predicates and migrate configuration; bound retries for every rejection path, including all-values-excluded cases.
- [ ] 7.4 Verify normal/unique separation, enable/disable/reset, exclusions, instance isolation and exhaustion; record changed configuration APIs.

## Task 8: Integrate portable string and regex generation

- **Status:** pending
- **Depends on:** Task 7
- **Size:** M
- **Can run in parallel with:** Task 9
- **Docs:** [implementation.md#task-8](implementation.md#task-8)

### Subtasks

- [ ] 8.1 Adopt the validated common parser/generator and route `StringProvider`/`FakerService.regexify` through it.
- [ ] 8.2 Preserve preprocessing at address, bank, finance, vehicle and cryptography call sites; specify retained Regex-overload option behavior.
- [ ] 8.3 Replace RgxGen KDoc references with supported syntax and remove its runtime dependency after all callers migrate.
- [ ] 8.4 Verify corpus coverage, independent output invariants, seeded equality and fail-loud unsupported syntax without RNG advancement.

## Task 9: Isolate JVM class-generation APIs

- **Status:** pending
- **Depends on:** Task 6
- **Size:** M
- **Can run in parallel with:** Tasks 7, 8
- **Docs:** [implementation.md#task-9](implementation.md#task-9)

### Subtasks

- [ ] 9.1 Remove reflection-only types/configuration from shared-facing `Faker`/`FakerConfig`; define a documented JVM class-generation factory/facade.
- [ ] 9.2 Keep `RandomClassProvider`, `ParameterInfo`, class-generation helpers and Java `FunctionalUtil` in JVM sources.
- [ ] 9.3 Adapt JVM consumers and record deliberate API breaks and migration calls.
- [ ] 9.4 Verify class-construction/Java tests and absence of JVM-only types or throwing target stubs in the common contract.

## Task 10: Port UUID, collections and text utilities

- **Status:** pending
- **Depends on:** Tasks 6, 8
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-10](implementation.md#task-10)

### Subtasks

- [ ] 10.1 Finish common UUID and collection sampling/shuffling paths in `RandomService`/`IRandom`/`RandomProvider`, specifying UUID version and byte order.
- [ ] 10.2 Replace JVM formatting and ambiguous character classes in `Internet`/`helper/Char.kt`; retain explicit transliteration semantics.
- [ ] 10.3 Audit money and other formatting consumers for controlled randomness and stable output.
- [ ] 10.4 Verify UUID/range/ordering/MAC/Unicode invariants plus cross-target reference vectors.

## Task 11: Port birth dates and random timestamps

- **Status:** pending
- **Depends on:** Tasks 9, 10
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-11](implementation.md#task-11)

### Subtasks

- [ ] 11.1 Record exact common date/timestamp return types, retained offsets, precision, bounds and clock defaults in the design before changing public signatures.
- [ ] 11.2 Port `Person` and the date operations in `IRandom`/`RandomService`/`RandomProvider`, including explicit reference-time inputs.
- [ ] 11.3 Add necessary JVM conversions and adapt class-generation callers and API baselines.
- [ ] 11.4 Verify age/leap-day/bounds/offset semantics and equal seeded output with fixed reference times on every target.

## Task 12: Port locale-aware random strings

- **Status:** pending
- **Depends on:** Tasks 1, 5, 11
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-12](implementation.md#task-12)

### Subtasks

- [ ] 12.1 Generate required deterministic exemplar tables from pinned build-time ICU data, retaining all current character-option flags.
- [ ] 12.2 Settle tags beyond bundled dictionary locales and empty-table handling explicitly; document any accepted-input change.
- [ ] 12.3 Port locale parameters/delegations and remove runtime ICU once all consumers use common data.
- [ ] 12.4 Verify complete bundled-locale coverage, Unicode cases, options and seeded equality without consumer ICU dependencies.

## Task 13: Expose the full core API from common code

- **Status:** pending
- **Depends on:** Tasks 7, 8, 9, 10, 11, 12
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-13](implementation.md#task-13)

### Subtasks

- [ ] 13.1 Close all portable dependencies of `Faker`, move the facade/bases/ordinary providers to common sources and keep Java helpers JVM-only.
- [ ] 13.2 Introduce/use portable-library target conventions and correct common/JVM dependency scopes without enabling targets on JVM-only modules.
- [ ] 13.3 Add common type-checked provider cases and semantic tests, retaining useful existing JVM coverage.
- [ ] 13.4 Publish real core artifacts locally and replace the fixture's copied core behavior with coordinate-based consumption; run full target behavior checks.

## Task 14: Port books and education providers

- **Status:** pending
- **Depends on:** Task 13
- **Size:** M
- **Can run in parallel with:** Tasks 15, 16, 17, 18, 19
- **Docs:** [implementation.md#task-14](implementation.md#task-14)

### Subtasks

- [ ] 14.1 Move `faker/books` and `faker/edu` implementations to common sources and expose core through common API dependencies.
- [ ] 14.2 Adapt nested education/book factories and additional constructor dependencies; preserve cross-category name lookup.
- [ ] 14.3 Add common invocation cases and focused nested/fallback/seeded tests for both modules.
- [ ] 14.4 Consume real books/education variants and remove the pilot's remaining copied domain implementation.

## Task 15: Port commerce and technology providers

- **Status:** pending
- **Depends on:** Task 13
- **Size:** M
- **Can run in parallel with:** Tasks 14, 16, 17, 18, 19
- **Docs:** [implementation.md#task-15](implementation.md#task-15)

### Subtasks

- [ ] 15.1 Move `faker/commerce` and `faker/tech` to common sources/dependencies and typed factories.
- [ ] 15.2 Adapt Finance/Bank/Company/Vehicle behavior to the common utilities without losing argument/preprocessing semantics.
- [ ] 15.3 Cover every shared function through typed cases and focused banking/card/plate pattern tests.
- [ ] 15.4 Verify published variants and seeded/semantic behavior on JVM, JS and iOS.

## Task 16: Port creatures, games and Japanese media

- **Status:** pending
- **Depends on:** Task 13
- **Size:** M
- **Can run in parallel with:** Tasks 14, 15, 17, 18, 19
- **Docs:** [implementation.md#task-16](implementation.md#task-16)

### Subtasks

- [ ] 16.1 Move `faker/creatures`, `faker/games` and `faker/japmedia` to common sources/dependencies and explicit factories.
- [ ] 16.2 Preserve primary/secondary aliases, nested properties and Unicode values.
- [ ] 16.3 Register typed invocation cases for every shared function and retain focused behavior tests.
- [ ] 16.4 Verify all three artifacts and cross-target seeded results; split newly discovered non-mechanical work before expanding this task.

## Task 17: Port databases, humor, lorem and miscellaneous providers

- **Status:** pending
- **Depends on:** Task 13
- **Size:** M
- **Can run in parallel with:** Tasks 14, 15, 16, 18, 19
- **Docs:** [implementation.md#task-17](implementation.md#task-17)

### Subtasks

- [ ] 17.1 Move the four modules to common sources/dependencies and explicit factories.
- [ ] 17.2 Preserve arguments, defaults and literal content that resembles expression syntax.
- [ ] 17.3 Add typed invocation coverage and punctuation/hash/Unicode regression cases.
- [ ] 17.4 Verify published variants and deterministic behavior for every module on each target.

## Task 18: Port movies, music and television providers

- **Status:** pending
- **Depends on:** Task 13
- **Size:** M
- **Can run in parallel with:** Tasks 14, 15, 16, 17, 19
- **Docs:** [implementation.md#task-18](implementation.md#task-18)

### Subtasks

- [ ] 18.1 Move `faker/movies`, `faker/music` and `faker/tvshows` to common sources/dependencies, retaining current package names.
- [ ] 18.2 Adapt factories and nested providers and preserve dedicated behavior tests such as Star Wars cases.
- [ ] 18.3 Cover every shared function with typed common invocations and seeded cases.
- [ ] 18.4 Verify all three published modules execute on every advertised target.

## Task 19: Port sports and travel providers

- **Status:** pending
- **Depends on:** Task 13
- **Size:** M
- **Can run in parallel with:** Tasks 14, 15, 16, 17, 18
- **Docs:** [implementation.md#task-19](implementation.md#task-19)

### Subtasks

- [ ] 19.1 Move `faker/sports` and `faker/travel` to common sources/dependencies and explicit factories.
- [ ] 19.2 Preserve arguments, default enums and category references.
- [ ] 19.3 Add typed invocation catalogues and focused semantic/seeded tests.
- [ ] 19.4 Verify both modules through published coordinates on all targets.

## Task 20: Preserve JVM pictures, extensions and CLI

- **Status:** pending
- **Depends on:** Tasks 14, 15, 16, 17, 18, 19
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-20](implementation.md#task-20)

### Subtasks

- [ ] 20.1 Adapt pictures, BLNS, Kotest extension and Java helper callers to migrated core while retaining their JVM artifact boundaries.
- [ ] 20.2 Update CLI introspection/native-image configuration where changed constructors or APIs require it.
- [ ] 20.3 Verify existing JVM integration, avatar conversion and class-generation usage tests.
- [ ] 20.4 Run existing CLI tests/native-image smoke paths and record target availability in migration documentation.

## Task 21: Verify the complete publication and BOM graph

- **Status:** pending
- **Depends on:** Task 20
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-21](implementation.md#task-21)

### Subtasks

- [ ] 21.1 Finalize production publication conventions/root-POM behavior and the BOM for independently versioned core/domain artifacts.
- [ ] 21.2 Convert the feasibility fixture into maintained external-coordinate consumer checks and remove superseded prototype code.
- [ ] 21.3 Validate metadata discovery, target artifacts and transitive core APIs without project substitution or Maven Local fallback.
- [ ] 21.4 Run KMP and JVM Kotlin/Java/Maven consumers; verify JVM-only modules advertise no unsupported target variants.

## Task 22: Enforce target execution and publication coverage in CI

- **Status:** pending
- **Depends on:** Task 21
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-22](implementation.md#task-22)

### Subtasks

- [ ] 22.1 Update PR/master/reusable workflows for explicit JVM, JS browser/Node, iOS simulator and published-consumer checks.
- [ ] 22.2 Update actual publication filters to include every advertised target once, preserving existing signing/release controls.
- [ ] 22.3 Preserve API/formatting/JVM/CLI checks and add the coverage measurement selected in Task 1 with honest target limitations.
- [ ] 22.4 Verify skipped/missing target jobs cannot report KMP success and each required check produces traceable evidence.

## Task 23: Document common usage and major-version migration

- **Status:** pending
- **Depends on:** Task 22
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-23](implementation.md#task-23)

### Subtasks

- [ ] 23.1 Update README, relevant MkDocs pages/navigation and tested common dependency examples.
- [ ] 23.2 Document all public API changes, target/module boundaries, supported regex grammar and deterministic-input conditions.
- [ ] 23.3 Update CONTRIBUTING provider/source-set/factory/dictionary/common-test instructions and affected stale paths.
- [ ] 23.4 Verify examples through consumer tests and run the repository's MkDocs check.

## Task 24: Complete verification and conformance review

- **Status:** pending
- **Depends on:** Task 23
- **Size:** S
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-24](implementation.md#task-24)

### Subtasks

- [ ] 24.1 Run `$kk:test` for the agreed behaviors, coverage evidence and required repository/release checks; broaden only for named unresolved risks.
- [ ] 24.2 Run `$kk:document` for public/contributor documentation and `$kk:review-code Kotlin` for implementation review.
- [ ] 24.3 Run `$kk:review-spec kotlin-multiplatform` against design, implementation and tasks, resolving material deviations.
- [ ] 24.4 Record any remaining finding with scope, reason and concrete next action; material acceptance failures block completion.
- [ ] 24.5 Link release evidence and move completed documents to `docs/feat/done/kotlin-multiplatform/`, updating references only when implementation is complete.

## Dependency Graph

```text
1 -> 2 -> 3 -> 4 -> 5 -> 6
                       6 -> 7 -> 8 -> 10
                       6 -> 9
                      [9,10] -> 11
                    [1,5,11] -> 12
            [7,8,9,10,11,12] -> 13
                               |-> 14 --+
                               |-> 15 --|
                               |-> 16 --|
                               |-> 17 --+-> 20 -> 21 -> 22 -> 23 -> 24
                               |-> 18 --|
                               +-> 19 --+
```

Square brackets denote prerequisites that must all complete. The per-task dependency fields are authoritative; this graph is a static planning aid. Task 10 also has Task 6 as an explicit prerequisite, already reachable through Task 8.
