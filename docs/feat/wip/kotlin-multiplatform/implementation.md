# Kotlin Multiplatform implementation plan

This plan implements the [design](design.md) for [#226](https://github.com/serpro69/kotlin-faker/issues/226). Execution state belongs in [tasks.md](tasks.md). No implementation task is complete merely because these documents exist.

## Execution boundaries

Tasks 1–4 form the first milestone: an isolated feasibility slice, including publication and consumption. Tasks 5–13 integrate the proven behavior into core. Tasks 14–19 migrate domain groups. Tasks 20–24 complete ecosystem compatibility, release artifacts, CI, documentation and verification.

During production refactoring, keep the existing JVM facade buildable. Do not move `Faker` to `commonMain` while its property types still depend on JVM-only declarations. First decouple/port individual mechanisms and utilities; Task 13 closes the common dependency graph and moves the public facade. The initial standalone fixture provides earlier cross-target evidence without adding a temporary public API to production core.

The fixture is deliberately temporary implementation code under `test/fixtures/kmp-feasibility/`, with its own Gradle settings. It reads the real YAML inputs and exercises candidate mechanisms. Its results do not prove production integration. Once production is consumable, replace copied prototype behavior with dependencies on locally published production artifacts and remove superseded prototype files. Keep useful consumer and reference-vector tests.

A task's size counts non-mechanical implementation changes, not source-set moves, repeated provider factory registrations or test-case registrations. Provider-group tasks are M because the runtime contract is already established; unexpected new algorithms require splitting the task before execution, not silently turning it into L.

Every task records `Step → verify` evidence. Use captured execution for builds/tests as required by repository tooling. Preserve failures and logs; never rerun merely to recover lost output. Existing JVM behavior can be a characterization oracle, but semantic invariants and agreed major-release changes take precedence over blindly preserving bugs.

## Task 1

**Inventory the real inputs and select a compatible trial toolchain.**

Read `gradle/libs.versions.toml`, `gradle/wrapper/gradle-wrapper.properties`, convention plugins and the runtime dependencies identified in the design. Record the Kotlin/Gradle/plugin/serialization/datetime tuple used for the fixture, with version-specific primary sources. The versions researched during design are candidates, not approved compatible pins. Keep JDK 21 compilation and Java 11 runtime unless a demonstrated requirement necessitates revisiting them.

Inventory all YAML scalar/container shapes, anchors, base/exact locale keys and expressions. Inventory regex patterns at the actual call sites after preprocessing, including public examples and malformed inputs. Identify needed grammar constructs and the exact APIs that currently accept locale tags, regex exclusions and Java types. Record these results in this feature directory as `feasibility.md`, linked from tasks; do not create a general migration framework.

Inspect current checks and coverage instrumentation. Repository guidance requires 100% coverage for new code, but the inspected build files do not expose a coverage plugin; settle the measurement mechanism and its target limitations before implementing new production algorithms. Do not treat a green test run as measured coverage.

Step → verify: a reproducible inventory accounts for all current dictionary files and real generation paths; no unsupported pattern or toolchain compatibility assumption is hidden. Compile a minimal dependency probe through the fixture when selecting the tuple, recording actual results and any missing SDKs.

## Task 2

**Prove the full embedded payload and seeded name generation.**

Create the standalone fixture under `test/fixtures/kmp-feasibility/`. Reuse the real inputs and anchor-resolving conversion from `buildSrc/src/main/kotlin/yaml-to-json.gradle.kts`. Implement bounded generated JSON suppliers and a small common decoder/resolver sufficient for a name path. Add the candidate controlled seeded stream and explicit selection operation; keep data generation/loading RNG-free.

Compile the full dataset even though the first API path is small. Exercise all logical resource suppliers so unreachable data cannot be silently dropped from the packaging test. Include the large Japanese address input in compiler-limit checks. Record chunking, generated source sizes, artifacts, initialization and decoded-category counts.

Step → verify: full-payload JVM, JS and iOS compilation; name generation returns valid dictionary values; golden numeric/name vectors agree across targets; first access does not decode unrelated locales. A missing iOS toolchain is an evidence gap, not a pass.

## Task 3

**Complete the representative generation slice.**

Extend the fixture with address fallback and cross-category references, a book-author path, explicit unique-view construction and the corpus-driven portable regex generation candidate. Use real `Name`, `Address` and `Book` behavior as the specification rather than only a synthetic name lookup. Cover exact/base locales, partially localized data and the phone/currency special cases needed to validate the resolver design.

Define supported syntax and failure diagnostics before implementing pattern handling. Validate all collected generation patterns; representative seeded generation tests complement syntax validation. Include exhaustion/reset and interleaved normal/unique calls. Record unsupported corpus constructs as blockers rather than silently narrowing support.

Step → verify: semantic cases and identical seeded output on JVM, JS and iOS; unsupported syntax fails before RNG consumption; output matches the specified patterns. The fixture demonstrates the architecture, not complete provider parity.

## Task 4

**Prove candidate publication and make the feasibility decision.**

Publish fixture core/domain artifacts to a project-local Maven repository and consume them from a separate fixture build through coordinates. Exercise JS browser/Node and iOS simulator execution as available in the chosen target matrix. Compare root metadata, target variants and a JVM Maven consumer. Test root-POM JVM delegation with Gradle metadata preserved before deciding whether the existing production helper can remain.

Complete `feasibility.md` with measured costs, commands, versions and outcomes. Select embedded JSON or document a justified representation revision. Freeze the tested regex grammar, seeded sampling specification and intended dependency tuple before broad production migration. If satisfying the corpus requires a general regex engine, revisit the accepted scope instead of introducing one implicitly.

Step → verify: independent resolution and execution succeed without project substitution, asset copying or network access during generation. All hard feasibility criteria in the design are satisfied. Unavailable checks remain explicit blockers to this milestone.

## Task 5

**Integrate dictionary generation and common resolution.**

Refactor `yaml-to-json.gradle.kts` into declared, reproducible generation tasks and wire common generated sources in `core/build.gradle.kts`. Introduce common dictionary decoding/lookup code under `core/src/commonMain/kotlin/io/github/serpro69/kfaker/dictionary/`. Refactor `FakerService.kt`, `Mapper.kt` and `Dictionary.kt` so the JVM path uses that same implementation while existing public facades remain buildable.

Preserve overlay precedence, aliases, child categories and special phone/currency/separator cases. Remove classloader and Java matching/map assumptions from the shared resolver. Keep YAML authoring paths stable. Load immutable resources lazily and maintain merged caches per runtime; explicit data errors include useful key context.

Step → verify: focused `FakerServiceTest` characterization and portable resolver tests run against the full production-generated corpus; cross-category lookup works without a domain class instance. Production and fixture results agree for the declared cases.

## Task 6

**Integrate controlled seeded primitive generation.**

Introduce the approved common seeded implementation and sampling rules under `core/.../kfaker/random/`. Refactor `FakerConfig`, `AbstractFaker`, `RandomService`, `IRandom` and the primitive delegation in `RandomProvider` around per-Faker state. Split out JVM utility methods without moving the entire public interface prematurely. New instances built from the same seed/configuration start independently.

Route library-owned selection, byte generation, ranges and shuffles through controlled operations. Inventory direct `config.random` and collection `random`/`shuffled` calls; replace the ones that would delegate promised behavior to a mutable stdlib algorithm. Preserve the documented precedence of a seed over an injected random source. Document custom-source and caller-collection conditions.

Step → verify: golden vectors for boundaries, negative/large seeds, bytes, floats/doubles and bounded selection; independent instances and warmed caches reproduce; invalid ranges fail explicitly. Same vectors pass across the fixture targets and production JVM.

## Task 7

**Make uniqueness portable without changing normal-provider state.**

Refactor `LocalUniqueDataProvider.kt`, `UniqueProviderConfiguration.kt`, `GlobalUniqueDataProvider.kt` and `AbstractFakeDataProvider.kt`. Replace reflective construction with explicit provider factories and replace return-type inspection with common configuration identities. Update registrations mechanically, including providers with additional constructor dependencies.

Resolve the exclusion contract before finalizing this public API: specify portable pattern semantics or deterministic caller predicates, migration from existing regex overloads, and whether JVM-only convenience overloads remain. Record this decision in `design.md`; do not promise parity while using unchecked platform regex behavior. Maintain provider/function global scopes and local precedence, and characterize existing reset/wrapped-call behavior. Bound every rejection path, including exclusions that currently recurse before retry-limit checks; no all-values-excluded case may loop or overflow indefinitely.

Step → verify: repeated/interleaved `.unique`, ordinary calls, global enable/disable, reset, excluded values, predicates/patterns and exhaustion have common semantic tests and equal seeded vectors. Two Faker instances do not share exclusion or used-value state.

## Task 8

**Integrate portable template and regex generation.**

Adopt the proven parser/generator under core common code and route `StringProvider` and `FakerService.regexify` through it. Preserve explicit preprocessing in `Address`, `Bank`, `Finance`, `Vehicle` and `CryptographyProvider`; changing those steps can alter pattern meaning. Update `StringProvider` KDoc from the RgxGen reference to the actual supported grammar.

Validate the complete generation corpus as a build/test input. Define behavior for Regex options if retaining a Regex overload; never silently discard flags. Remove the runtime RgxGen dependency only after every production caller uses the common path.

Step → verify: generated values satisfy independent pattern invariants, seeded output matches across targets, unsupported/malformed patterns report precise errors without advancing the random stream, and all shipped generation patterns are covered.

## Task 9

**Separate JVM arbitrary-class APIs from shared configuration.**

Refactor `Faker.kt`, `FakerConfig.kt`, `FakerExt.kt`, `RandomClassProvider.kt` and `ParameterInfo.kt`. Keep class inspection and Java helpers under JVM sources. Select and document a JVM factory/facade and its configuration entry point; common configuration must not contain `RandomProviderConfig`, `KType`/constructor inspection or hidden runtime casts to JVM extensions.

Adapt class generation to consume portable primitive/date utilities through explicit JVM conversions where required. Keep Java `Class` conveniences JVM-only. Update JVM class-generation examples and API baselines as intentional major changes.

Step → verify: existing `RandomClassProviderTest` and Java usage still exercise a supported JVM API; a common API inspection contains no JVM-only type references. Do not add JS/iOS stubs throwing unsupported-operation exceptions.

## Task 10

**Port UUID, collection and text-dependent utilities.**

Finish common utility paths in `RandomService`, `IRandom`, `RandomProvider`, `Internet` and `helper/Char.kt`. Use common formatting for MAC/UUID output and explicit character-class semantics for text preparation. Record the selected UUID version/format as a deliberate major-release change; do not preserve the current version-bit/name-UUID mismatch by accident.

Make subset/shuffle ordering and supplied-collection preconditions explicit. Audit `Money` and other formatting consumers against the controlled range and string operations. Mechanical delegation changes do not create new random streams.

Step → verify: UUID structure and deterministic sequences, numeric range boundaries, ordered collection sampling, MAC formatting and Unicode/transliteration fixtures agree on all targets. Verify semantics independently of the shared golden output.

## Task 11

**Port birth dates and timestamp generation.**

Finalize the exact common return types in `Person`, `IRandom`, `RandomService` and `RandomProvider`, using the validated date dependency tuple. Record signatures, offset handling, supported ranges, precision and default clock behavior before moving callers. Birth dates use a common date type; timestamps must preserve or explicitly separate offset information rather than losing it during conversion.

Provide explicit reference-time inputs for past/future generation and deterministic tests. Keep necessary Java date conversions in JVM helpers and adapt JVM class generation. Use UTC/fixed offsets for current functionality rather than adding a new named-timezone feature.

Step → verify: fixed-reference birth-date ages, leap-day boundaries, timestamp bounds and offset conversion; every target runs the same cases and returns identical seeded values. Update affected JVM baselines and migration entries.

## Task 12

**Port locale-aware random strings.**

Extend build-time generation with pinned ICU exemplar data needed by `RandomService.randomString`. Keep reproducible data version/ordering and existing main/auxiliary/index/punctuation flags. Refactor locale parameters to common language tags and update `IRandom`/`RandomProvider` delegations.

Use the Task 1 inventory to settle tags outside dictionary locales. Preserve supported behavior where practical; record any proposed narrower accepted-input contract before adopting it. Handle empty exemplar sets explicitly. Remove runtime ICU only after consumers and dependencies have been audited.

Step → verify: all bundled locales have an explicit table/fallback result, option combinations produce valid characters, Unicode boundaries are covered, and fixed seeds agree across platforms. Character-table generation does not require consumer-side ICU.

## Task 13

**Expose the complete core API in common code.**

Move the now-portable core facade, provider bases, dictionary types, exceptions and ordinary providers into common sources. Close all remaining dependencies from `Faker`, including money, phone/currency special providers, files, colors, gender, IDs and measurement. Apply common compiler annotations/imports as supported by the selected toolchain; retain Java helpers only in JVM sources.

Enable production JVM/JS/iOS targets through a portable-library convention, leaving JVM-only modules on the existing convention. Scope source-set dependencies correctly. Add type-checked common smoke cases and behavioral tests; preserve the JVM reflection suite as additional coverage rather than using it as evidence for other targets.

Publish actual core artifacts locally and switch the core feasibility consumer to those coordinates, removing copied core prototype implementations. This is the first production integration gate before broad domain migration.

Step → verify: common consumers call every ordinary core provider, all locales remain packaged, no shared API references Java/reflection-only types, and actual published core runs the seeded/semantic suite on each target.

## Task 14

**Port books and education, including nested providers.**

Migrate `faker/books` and `faker/edu` to common sources/dependencies. Adapt explicit unique factories and nested provider constructors, especially `Educator` and book providers with random-service dependencies. Verify cross-category author/name references through core data resolution. Move the pilot domain consumer to the real books artifact and remove its remaining copied prototype implementation.

Step → verify: each shared public function has a type-checked invocation case; nested education paths, localized fallback and seeded book authors work from independent published common consumers.

## Task 15

**Port commerce and technology, including regex-backed providers.**

Migrate `faker/commerce` and `faker/tech`. Adapt `Finance`, `Bank`, `Company`, `Vehicle` and their constructor/delegation requirements to the proven common APIs. Keep the existing preprocessing and argument behavior visible in tests, and use common API dependencies on core.

Step → verify: bank, credit-card and state-specific plate paths cover the actual pattern corpus, while other provider functions have type-checked shared cases and deterministic outputs. Verify both module artifacts resolve and execute on all targets.

## Task 16

**Port creatures, games and Japanese media.**

Migrate `faker/creatures`, `faker/games` and `faker/japmedia`. Preserve primary/secondary category mapping, nested provider properties and Unicode dictionary values. Most edits are source-set moves and explicit factory registrations; isolate any newly discovered bespoke algorithm into a smaller task before proceeding.

Step → verify: type-checked common invocation catalogues cover each public shared provider function, nested category aliases resolve, and each artifact has target execution and seeded cases.

## Task 17

**Port databases, humor, lorem and miscellaneous providers.**

Migrate `faker/databases`, `faker/humor`, `faker/lorem` and `faker/misc`. Preserve argument/default handling and literal data that resembles expression syntax. Do not expand scope into editorial changes to dictionary content.

Step → verify: common invocation cases exercise every exposed function with representative arguments; literal hashes, punctuation and Unicode are preserved where they are data rather than templates; seeded outputs agree.

## Task 18

**Port movies, music and television providers.**

Migrate `faker/movies`, `faker/music` and `faker/tvshows`, retaining package names such as the existing `io.github.serpro69.kfaker.tv` package. Adapt constructor dependencies and nested paths; preserve existing dedicated tests such as the Star Wars cases alongside common checks.

Step → verify: all shared public functions have executable invocation cases, domain-specific invariants remain valid, and the three published artifacts execute across the target matrix.

## Task 19

**Port sports and travel providers.**

Migrate `faker/sports` and `faker/travel`, including arguments/default enums and cross-category lookups. Apply the same common dependency, uniqueness and test conventions as the preceding domain slices.

Step → verify: common invocation catalogues and focused existing behavior checks pass, with seeded equality and artifact execution for both modules on every supported target.

## Task 20

**Preserve JVM pictures, extensions and CLI integration.**

Keep `faker/pictures`, `extension/blns`, `extension/kotest-property`, Java helpers and CLI behavior JVM-specific. Adapt their dependencies and API calls to the new core. Preserve BLNS's own JSON/resource handling unless a change is actually needed for compatibility. Do not publish misleading JS/iOS variants for these JVM-only modules.

Update CLI introspection and native-image configuration where changed provider constructors/public methods require it. Preserve functionality rather than broadly removing reflection from the CLI. Extend migration documentation for JVM class generation and extension users.

Step → verify: existing extension tests, image conversion/avatar tests, Java examples, CLI tests and the existing native-image smoke paths work with the migrated library. Platform documentation identifies these modules accurately.

## Task 21

**Verify the complete publication and BOM graph.**

Finalize `faker-pub-conventions.gradle.kts`, `utils/Publishing.kt`, common provider conventions and `bom/build.gradle.kts` using actual core/domain artifacts. Account for separately versioned modules and expose core transitively wherever public inheritance requires it. Apply the root-POM decision proven in Task 4 to production, preserving or documenting Maven/JVM coordinates.

Convert `test/fixtures/kmp-feasibility` into a maintained consumer fixture, or move it to a clearly named published-consumer fixture with links updated. Remove all superseded prototype implementations. Resolve every advertised variant from a project-local repository with no project substitution or accidental Maven Local fallback.

Step → verify: KMP common consumers, JVM Kotlin/Java and Maven/BOM consumers resolve the expected dependency graph and execute representative providers; all target artifacts referenced by metadata exist; excluded modules advertise JVM only.

## Task 22

**Enforce actual target coverage in CI.**

Update `.github/workflows/pr.yml`, `master.yml` and `run_gradle.yml` to run explicit JVM, JS browser/Node and iOS simulator tasks plus consumer checks. The current snapshot workflow selects only root and JVM publications; update actual publication filters so every advertised target is published exactly once. Keep existing API, formatting, JVM host/minimum/maximum and CLI release requirements. Ensure filtering and final job aggregation cannot count an accidentally skipped target as successful KMP coverage.

Use type-checked common invocation cases instead of runtime reflection for cross-target provider smoke coverage. Retain focused unit tests for meaningful branches and failure modes. Implement the coverage measurement selected in Task 1; disclose target instrumentation limitations rather than claiming unmeasured 100% coverage.

Step → verify: each required target produces named test evidence; intentionally failing one consumer or seeded vector fails the corresponding check; local commands and CI select the same source sets and versions. Avoid repeated broad runs without a new failure or unresolved risk.

## Task 23

**Publish the user-facing migration and contributor guidance.**

Update `README.md`, `CONTRIBUTING.md`, relevant `docs/pages/data-provider/core/` and `docs/pages/fakers/` pages, and the actual MkDocs navigation/includes. Add a next-major migration page covering common dependencies, supported targets/modules, class-generation configuration, date/locale/enum types, uniqueness configuration, regex syntax, UUID behavior and seeded guarantees.

Update provider authoring instructions for common sources, explicit factories, common tests and generated dictionaries. Correct outdated build/source paths encountered in those affected instructions; do not rewrite unrelated documentation. Mark all deliberate breaks against the checked API baseline.

Step → verify: documented examples are exercised by common/JVM consumer tests, supported modules match metadata, and the existing MkDocs build succeeds. No docs claim stability or target support that lacks evidence.

## Task 24

**Complete release verification and conformance review.**

Invoke `$kk:test` for the meaningful shared behaviors and required repository/release suites; `$kk:document` for changed public/contributor behavior; `$kk:review-code Kotlin` for the implementation; and `$kk:review-spec kotlin-multiplatform` against all design/implementation/task documents. Review generated artifacts and API changes, not only source compilation.

Sufficient evidence is the target behavior matrix, complete corpus/pattern coverage, equal seeded vectors with semantic assertions, published consumer results, coverage report, required existing checks and documented migration. Broader verification is justified by shared-runtime regressions, unsupported real inputs, publishing mismatches, unexpected target differences or changed toolchains. Do not use retries to dismiss reproducible failures.

Record unaddressed findings durably with the affected scope, reason and next action. Material violations of the agreed scope block closure. Move the completed feature documents to `docs/feat/done/kotlin-multiplatform/` and update links when the implementation is actually complete. Closing the GitHub issue or publishing a remote release is a separate authorized repository action, not an effect of this design task.

Step → verify: every acceptance item in the design has linked evidence and every implementation task is complete; independent reviews have no unresolved material gaps.

## Deferred decisions are explicit work

The exact toolchain/library pins, full regex grammar, exclusion-matching contract, date return shape, JVM factory naming and treatment of locale tags outside the dictionary set remain assigned to Tasks 1, 4, 7, 9, 11 and 12. They are unresolved design gates, not silently postponed features. Update this plan and the design with their evidence before dependent implementation proceeds.
