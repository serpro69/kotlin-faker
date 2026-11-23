# Kotlin Multiplatform implementation plan

This plan implements the [design](design.md) for [#226](https://github.com/serpro69/kotlin-faker/issues/226). Execution state belongs in [tasks.md](tasks.md). No implementation task is complete merely because these documents exist.

## Execution boundaries

Tasks 1–4 plus Task 25 form a **mechanism-only** feasibility milestone: full payload, seeded operations, regex generation and one minimal published probe. Existing task IDs remain stable for review traceability; additional Tasks 25–31 are placed in execution order rather than numerical order.

Task 26 adopts the vetted production toolchain and enables core compile/test targets; Task 31 installs required CI, coverage and publication controls before Tasks 5–12 extract behavior. Keep the public JVM facade in `jvmMain` until its dependencies are common; it can delegate into common code, never the reverse. Each component's common dependency graph must close in the task introducing it, through plain configuration values and common interfaces. Run tests in `core/src/commonTest` against the **actual changed production sources** on JVM, JS browser/Node and an iOS simulator at every slice. A passing copied prototype is not acceptable evidence.

The small standalone harness at `test/fixtures/kmp-feasibility/` never contains Faker facades, locale overlay/resolver logic, provider classes or uniqueness. Candidate generator/data/regex components can be promoted once into production, with their obsolete probe copies retired at that handoff. Do not maintain duplicate implementations or make repeated prototype removal a deliverable. The external consumer harness remains useful for real artifacts in Tasks 29 and 21. Keep its producer/settings outside the root project includes, release aggregation and BOM, with distinct local-only coordinates. Promote internal mechanisms only; probe APIs, metrics collectors and test scaffolding must not enter production artifacts or API dumps.

Task 30 explicitly promotes the common runtime/service/configuration/provider-base declarations after the component extractions. Tasks 27 and 28 then complete ordinary core provider paths; Task 13 only exposes the common facade. Task 29 validates actual core publications before Tasks 14–19 migrate domain groups. Tasks 20–24 finish JVM integrations, the full release graph, CI, docs and verification.

Task sizes exclude only repetition with unchanged logic: path moves, identical factory registrations and test-case registrations. A new generator, decoder, RNG, resolver, target convention or behavior test family counts as real work. Tasks 2/25/3 separate the original oversized spike; Tasks 26/27/28/13/29 separate the original oversized core closure. Split a slice again if inspection reveals more than five substantive implementation files.

Dependency fields express technical prerequisites. Shared edits to `RandomService`, `IRandom`, `RandomProvider` or Gradle files require coordination, not artificial functional dependencies: regex generation does not depend on uniqueness, and locale strings do not depend on dates. A parallel marker of “—” means no conflict-free schedule is promised; it does not add a dependency.

Each breaking-API slice updates its compiling downstream JVM callers and API baselines in the same change; Task 20 is final ecosystem verification, not permission to leave intermediate consumers broken. Exact release pins and stage-specific APIs are recorded before their dependent code changes. Task 1 is ready to start; no document review can substitute for the later feasibility results.

Every task records `Step → verify` evidence through captured execution. Preserve failures/logs. Existing JVM behavior can characterize a migration, but agreed major changes and independent semantic assertions take precedence over blindly preserving bugs.

Apply the design's variant/runtime verification matrix throughout: device iOS variants require compilation and representative consumer linking; iOS execution means the declared simulator runner. Browser and Node both execute shared tests. Task 1 records the exact runner/toolchain/task names, and no compile-only or skipped task counts as runtime evidence.

## Task 1

**Inventory inputs and select the trial/consumer/test toolchain.**

Read the version catalog, wrapper and convention plugins. Record the producer Kotlin/Gradle/Xcode/plugin/serialization/datetime tuple and explicit minimum **consumer Kotlin versions for JVM and KMP**, stdlib requirements and relevant language/API settings in `feasibility.md`. Explain any increase as a major-release cost; do not equate the compiler used to build the library with one universal consumer floor. Keep Java 11 runtime/JDK 21 compilation unless demonstrated constraints require revision.

Set up only the minimal standalone probe build. Choose `kotlin.test` annotations/assertions for `commonTest`, JVM JUnit 5 integration compatible with existing Kotest specs, and KGP JS/Native execution. Align the testing library with the selected Kotlin version. Existing DescribeSpec suites remain in `jvmTest`; no multiplatform Kotest/KSP runner is introduced. Verify one discovered common test actually executes per target before adding mechanisms.

Inventory YAML shapes/anchors/locales, actual post-preprocessing regex generation inputs, Java APIs, locale tag acceptance and raw-resource consumers. Inspect coverage instrumentation and select a measurement approach for the existing 100% new-code requirement, recording target limits rather than inferring coverage from test success. Record the macOS/Xcode/runner combination for simulator execution and single-host publication.

Step → verify: complete input/consumer inventory; version/API probes and common test discovery run on each target; candidate consumer floors are explicit and tested provisionally, with real-artifact checks assigned to Tasks 29/21. No SDK or version gap is treated as passing.

## Task 2

**Prove full payload generation and lazy decoding only.**

Use the Task 1 harness and real YAML inputs. Resolve anchors/aliases before partitioning locale-wide files into category projections; retain source identity and overlay priority in the generated manifest. Implement bounded JSON suppliers and a minimal accessor that decodes a requested projection without parsing unrelated categories. There is no name provider, dictionary expression resolver or random generator in this task.

Compile and enumerate the full payload, including Japanese address data, on JVM/JS/iOS. Read selected projection keys directly and verify complete source-to-projection content coverage, canonical ordering and lazy decoding, including a non-English file containing multiple categories. Record generated-source size, artifact sizes, compile/resource costs and decoded-category counts. Keep input YAML unchanged; candidate generated files are build outputs.

Step → verify: the complete payload compiles and is accessible on all targets without asset copying/network access; accessing one key does not eagerly decode every locale. Missing toolchains remain explicit evidence gaps.

## Task 25

**Prove controlled seeded operations independently of providers.**

In the existing probe harness implement the raw seeded generator and bounded integer/float/byte/shuffle operations as separate focused components. Specify seed initialization, overflow/bit rules, consumption order and independently derived vectors. This task owns no payload generation or provider logic.

Step → verify: boundary and rejection-sampling vectors, mixed operations and shuffle order match on JVM, JS and iOS. Record the complete sampling specification in `feasibility.md`; raw generator agreement alone is insufficient.

## Task 3

**Prove the corpus-driven regex grammar and generator.**

Use Task 1's post-preprocessing pattern inventory and Task 25's controlled stream in the probe. Specify and implement only the candidate parser/generator, documenting accepted constructs and diagnostic positions before implementing them. Do not add address fallback, book providers or unique factories.

Step → verify: every collected bundled generation pattern is classified/supported, representative generated outputs satisfy independent matching invariants, and identical seeded cases run on all targets. Unsupported patterns fail before RNG consumption. This is generation syntax; user-supplied uniqueness exclusions continue using Kotlin Regex.

## Task 4

**Consume one minimal probe artifact and decide feasibility.**

Publish a single small probe artifact exposing primitive-valued payload/random/regex checks to a project-local repository, under distinct probe coordinates outside release aggregation/BOM. Consume it in an independent KMP build using the declared consumer compiler floors. Execute JVM, JS browser/Node and iOS simulator checks. Do not invent prototype core/domain artifacts or duplicate the production POM/signing setup.

Complete `feasibility.md` with version/test/consumer floors, measurements, accepted regex grammar, seeded specification and the representation decision. This proves the basic KMP dependency mechanism only. Actual `Publishing.kt`, root-POM rewriting, domain dependencies/BOM and signing controls remain production evidence in Tasks 29/21/22.

Step → verify: minimal external-coordinate consumption, complete payload access and mechanism vectors work without substitution/downloads. All genuine mechanism gates have evidence; failed corpus/compile constraints require a design revision, not dropping locales.

## Task 26

**Enable production targets and common-test execution before extraction.**

Apply the Task 1 tuple to the production version catalog, wrapper and build plugins, verifying existing JVM checks before adding new behavior. Add/use a reusable portable-library convention through the existing Kotlin/JVM convention structure and `core/build.gradle.kts`. Declare JVM, JS browser/Node and iOS device/simulator targets on core now; leave JVM-only modules on their current convention. Scope JDK/reflect/JUnit/Kotest-runner dependencies to JVM sources and install the Task 1 kotlin.test setup in `commonTest`.

Initially retain all legacy facades in `jvmMain`. Introduce only the minimal common contracts needed by later slices: configuration values, resource lookup and random operations. Common interfaces cannot mention JVM Faker/service/configuration classes. A small actual production common test establishes discovery on each runner; later tasks add direct behavior tests to these same source sets.

Step → verify: production common tests execute on JVM, JS and iOS using the validated tuple, while the JVM facade and current JVM checks remain usable. Required automated enforcement and upload gating are completed in Task 31 before extraction.

## Task 31

**Enforce production target checks and keep incomplete releases closed.**

Extend the existing PR/reusable workflows with explicit JVM, JS browser/Node and iOS simulator jobs using Task 1's tested environments. Initially these cover core; task selection includes other portable modules as they opt in. Require evidence that shared tests execute; wire the selected coverage measurement before Task 5 introduces new production behavior. Record instrumentation limits separately from the required cross-target behavioral tests. Task 22 later completes the module/consumer and compiler-floor matrix rather than introducing target checks for the first time.

In the root build/publishing conventions guard every remote upload path: direct Maven uploads, NMCP aggregate uploads and the `publishToAppropriateCentralRepository` route. Guard dependent tag/release entry points too; skipping an upload must not make a release tag eligible. Check local staging separately from remote upload permissions, including existing snapshot/dev `onlyIf` rules so a successful-but-skipped local task is not mistaken for a staged artifact.

Keep remote migration publication disabled through Task 24. Task 21 proves the graph; Task 22 prepares the single Mac publisher but does not activate it. A later explicitly authorized release action can enable publishing after final verification, and must verify remote consumer coordinates before closing the issue.

Step → verify: required target/coverage jobs fail on a controlled failing case. Test the actual guard configuration with inert upload/tag actions in an isolated build and verify staging produces files. A dry-run shows the task graph but does not execute guard conditions, so it is not sufficient proof. Do not perform a remote upload or create a real tag to test this guard.

## Task 5

**Integrate common dictionary resolution and remove legacy resource packaging.**

Promote the proven generator/accessor from Task 2, declare reproducible task inputs/outputs, and wire generated common sources in `core/build.gradle.kts`. Add common dictionary lookup/decoding/resolution under `core/src/commonMain/kotlin/io/github/serpro69/kfaker/dictionary/`. The common resolver receives locale values, projected resource lookup and a common random-operation interface; the JVM `FakerService`/Mapper facade delegates into it without being referenced from common code. Decode by source/category projection and build immutable resolved views keyed by normalized locale, primary category, secondary resource identity and ordered overlays. Do not carry the mutable category-only cache into the single-runtime design. Preserve literal values and expression context without assuming Java-only regex constructs work on JS.

Preserve base/exact overlays, aliases, nested expressions and phone/currency/separator cases. Add production tests characterizing missing third-level localized keys, record the preserve-versus-scoped-fix decision in `feasibility.md`, and implement any required scoped correction with regression coverage. Update stale resolver KDoc to describe dictionary dispatch rather than reflected provider calls.

At the runtime cutover exclude `locales/**` YAML and generated JSON from main JVM resources and remove their generation/resource processing dependency when no longer used. Keep authoring YAML and explicit test fixtures. Replace `FakerIT`'s classpath-YAML enumeration with a generated locale manifest; adapt fixture lookup explicitly instead of relying on accidental classloader overrides. Audit repository consumers and record direct resource-path access as a packaging break. Unrelated BLNS/image assets remain intact.

Step → verify: actual production resolver/fallback/cross-category cases run on all targets, including reversed loading of sibling secondary resources and cold/warm caches with identical generation calls; existing JVM facade tests pass. Inspect the fresh JVM JAR for absence of legacy locale YAML/JSON and completeness of embedded data, and verify locale-enumeration tests still execute rather than becoming empty.

## Task 6

**Integrate controlled seeded primitive generation.**

Introduce the approved common seeded implementation and sampling rules under `core/src/commonMain/kotlin/io/github/serpro69/kfaker/random/`. Refactor `FakerConfig`, `AbstractFaker`, `RandomService`, `IRandom` and the primitive delegation in `RandomProvider` around per-Faker state. Split out JVM utility methods without moving the entire public interface prematurely. New instances built from the same seed/configuration start independently at the same raw sequence, even across Faker families; no automatic per-family seed mixing is introduced.

Route library-owned selection, byte generation, ranges and shuffles through controlled operations. Inventory direct `config.random` and collection `random`/`shuffled` calls; replace the ones that would delegate promised behavior to a mutable stdlib algorithm. Preserve the precedence of a seed over an injected random source. Reusing an injected Random object deliberately shares its state; scalar seeds create independent streams and can produce correlated choices across families. Add production tests for both modes, interleaving and precedence, and document custom-source/collection conditions.

Step → verify: golden vectors for boundaries, negative/large seeds, bytes, floats/doubles and bounded selection; independent instances and warmed caches reproduce; invalid ranges fail explicitly. The actual production common stream/operations run these vectors on every target enabled by Task 26; the JVM facade consumes that same implementation.

## Task 7

**Make uniqueness portable while retaining Kotlin Regex exclusions.**

Refactor local/global uniqueness state and the base provider wrapper into closed common components with typed factories; the legacy JVM facades delegate to them until moved. Replace return-type inspection with common identities, including factory dependencies for Internet, Dune and Finance.

Keep Kotlin `Regex.containsMatchIn` for caller exclusions. Document that seeded equality requires patterns/options to compile and make equivalent matching decisions on compared targets. Test representative portable patterns; do not build an exclusion DSL/matcher or tie this work to the regex generator. Maintain provider/function scopes, local precedence and characterized reset/wrapped-call behavior. Bound every rejection path, including all-values-excluded cases that currently bypass retry checks.

Step → verify: actual common state/wrappers execute normal/unique interleaving, enable/disable, clearing, portable exclusions and exhaustion on all targets; two Faker instances share no unique state. Existing JVM provider tests exercise the typed adapters. Record intentional configuration API changes.

## Task 8

**Integrate portable template and regex generation.**

Promote the proven parser/generator from Task 3 into production core common code and route `StringProvider` and `FakerService.regexify` through it. Preserve explicit preprocessing in `Address`, `Bank`, `Finance`, `Vehicle` and `CryptographyProvider`; changing those steps can alter pattern meaning. Update `StringProvider` KDoc from the RgxGen reference to the actual supported grammar.

Validate the complete generation corpus as a build/test input. Define behavior for Regex options if retaining a Regex overload; never silently discard flags. Remove the runtime RgxGen dependency only after every production caller uses the common path.

Step → verify: actual production common generator tests run on every Task 26 target, generated values satisfy independent pattern invariants, and unsupported syntax fails without RNG advancement. JVM StringProvider/provider adapters call this same implementation. Existing uniqueness wrappers can remain until Task 7; regex generation has no correctness dependency on that refactor.

## Task 9

**Separate JVM arbitrary-class APIs from shared configuration.**

Refactor `Faker.kt`, `FakerConfig.kt`, `FakerExt.kt`, `RandomClassProvider.kt` and `ParameterInfo.kt`. Keep class inspection and Java helpers under JVM sources. Select/document the JVM factory and move the now-clean `FakerConfig`/builder declarations into common sources; they must not contain `RandomProviderConfig`, constructor-inspection types or hidden JVM casts.

Adapt class generation to the already-available common primitive operations. Keep existing Java date and locale bridges working until Tasks 11 and 12 supply their replacements; this task must not depend on those future APIs. Task 11 owns the subsequent date-conversion change. Keep Java `Class` conveniences JVM-only. Update JVM class-generation examples and API baselines as intentional major changes.

Step → verify: existing `RandomClassProviderTest` and Java usage still exercise a supported JVM API; a common API inspection contains no JVM-only type references. Do not add JS/iOS stubs throwing unsupported-operation exceptions.

## Task 10

**Port UUID, collection and text-dependent utilities.**

Finish common utility paths in `RandomService`, `IRandom`, `RandomProvider`, `Internet` and `helper/Char.kt`. Use common formatting for MAC/UUID output and explicit character-class semantics for text preparation. Record the selected UUID version/format as a deliberate major-release change; do not preserve the current version-bit/name-UUID mismatch by accident.

Make subset/shuffle ordering and supplied-collection preconditions explicit. Audit `Money` and other formatting consumers against the controlled range and string operations. Mechanical delegation changes do not create new random streams.

Step → verify: tests of the actual production common utility functions run on every Task 26 target and verify UUID structure/sequences, ranges, collection order, MAC formatting and Unicode/transliteration. Verify semantics independently of the shared golden output.

## Task 11

**Port birth dates and timestamp generation.**

Finalize the exact common return types in `Person`, `IRandom`, `RandomService` and `RandomProvider`, using the validated date dependency tuple. Record signatures, offset handling, supported ranges, precision and default clock behavior before moving callers. Birth dates use a common date type; timestamps must preserve or explicitly separate offset information rather than losing it during conversion.

Provide explicit reference-time inputs for past/future generation and deterministic tests. Keep necessary Java date conversions in JVM helpers and adapt JVM class generation. Use UTC/fixed offsets for current functionality rather than adding a new named-timezone feature.

Step → verify: tests of the actual production common date functions run on every Task 26 target for fixed-reference ages, leap days, bounds and offsets, returning identical seeded values. Update affected JVM baselines and migration entries.

## Task 12

**Port locale-aware random strings.**

Extend build-time generation with pinned ICU exemplar data needed by `RandomService.randomString`. Keep reproducible data version/ordering and existing main/auxiliary/index/punctuation flags. Refactor locale parameters to common language tags and update `IRandom`/`RandomProvider` delegations.

Use the Task 1 inventory to settle tags outside dictionary locales. Preserve supported behavior where practical; record any proposed narrower accepted-input contract before adopting it. Handle empty exemplar sets explicitly. Remove runtime ICU only after consumers and dependencies have been audited.

Step → verify: tests of the actual production common locale-string functions run on every Task 26 target; all bundled locales have explicit table/fallback results, options and Unicode boundaries are covered, and fixed seeds agree. Character-table generation does not require consumer-side ICU.

## Task 30

**Close the common runtime and provider-base declarations.**

After Tasks 7–12, audit/promote the remaining `AbstractFaker`, `FakerService`, `IRandom`, `RandomService`, provider-base and uniqueness declarations into common sources. `FakerConfig` is already common from Task 9. Reuse the tested common components; this is declaration/composition closure, not another runtime implementation. Adapt public signatures to the decisions already recorded in Tasks 9–12, retaining only genuine Java conveniences as JVM helpers.

Wire exactly one runtime per Faker owner and remove superseded JVM forwarding implementations. Verify the actual constructor/property dependency graph, including `AbstractFakeDataProvider`, `YamlFakeDataProvider` and their annotation, dictionary-identifier, exception and simple-helper dependencies, rather than assuming directory moves make it portable. Do not bring the public `Faker` concrete-provider registry forward from Task 13.

Step → verify: instantiate and exercise the real common runtime and provider-base types on every target, with seeded/cache/unique state shared as specified and no JVM-only signature references. All concrete providers still in JVM sources must compile against these same promoted types. Task 27 may begin only once these prerequisites actually exist.

## Task 27

**Close dictionary-only and template core provider paths.**

Move the common-ready Name, Color, Currency, File, Gender, IdNumber, Measurement and Source providers, plus StringProvider/CryptographyProvider backed by Task 8, into common sources. Task 30 must already supply their common service/runtime/base types; this task does not invent new algorithms or close missing runtime declarations. Adapt repetitive factory registrations and add typed invocation coverage and remaining provider-specific assertions.

Step → verify: every listed provider function executes from actual common tests on all targets, with semantic/seeded cases and no JVM-only dependencies. Unexpected bespoke behavior is split into another small task rather than hidden in a mechanical move.

## Task 28

**Close composed and locale-special core provider paths.**

Move Address, Internet, Money, PhoneNumber, CurrencySymbol and Separator into common sources using the already-tested resolver and utility components. Wire additional provider dependencies such as Internet's Name reference. Confirm Person and RandomProvider's already-ported paths expose the common types required by the facade. Limit new work to composition and provider-level checks.

Step → verify: real common provider calls cover cross-category addresses, locale phone/currency behavior, money formatting, Internet composition and factory wiring on all targets. No missing ordinary provider type is deferred to Task 13.

## Task 13

**Expose the common Faker facade after provider closure.**

After Tasks 27/28, move only `Faker` and its builder/DSL facade into common sources, finish the public API surface/baselines and add a common consumer-style smoke test of the complete facade. Targets, shared-test setup, runtime algorithms and ordinary provider behavior are prerequisites, not hidden work in this task.

Step → verify: `Faker` is callable directly from common tests on all production targets; its public signatures contain no JVM-only types, normal/unique calls use the established runtime, and existing JVM/Java-facing checks remain supported.

## Task 29

**Consume actual core publications before domain migration.**

Stage actual core root and target artifacts using production publication conventions and `utils/Publishing.kt`. Prepare the shared opt-in domain convention before parallel module migrations: reuse portable targets/kotlin.test, declare common `api(core)`, and keep the existing `:test`/`testHelper` dependency JVM-test-only. Subsequent domain tasks apply that convention in their own build files instead of each modifying the shared convention. Point the retained minimal external consumer at these coordinates; it contains no copied provider/runtime implementation. Compile at the declared minimum JVM/KMP consumer Kotlin versions and execute representative core paths on all targets.

Test the JVM-delegating root POM and metadata discovery with independent KMP, JVM Kotlin/Java and Maven consumers. A Gradle POM-only check must disable metadata redirection too. Keep or revise the POM helper based on this evidence; Task 4's probe is not its oracle. Remote publication stays gated through final Task 24 verification.

Step → verify: staged core variants resolve without substitution/Maven Local fallback, run at the declared floors and retain every locale without duplicate resources. Check JAR/klib/source/API surfaces for probe entry points or scaffolding; these are excluded from production. Compile the opt-in domain convention and ensure the legacy JVM helper never becomes a common dependency.

## Task 14

**Port books and education, including nested providers.**

Migrate `faker/books` and `faker/edu` to common sources/dependencies. Adapt explicit unique factories and nested provider constructors, especially `Educator` and book providers with random-service dependencies. Verify cross-category author/name references through core data resolution. Extend the real-artifact consumer from Task 29 with books/education coordinates; there is no prototype domain implementation to retire.

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

Update CLI introspection and native-image configuration where changed constructors/public methods require it. In `resource-config.json`, audit and narrow the broad JSON include after Task 5 removes core locale resources; preserve any remaining required BLNS JSON, image assets and reflection resources. Preserve functionality rather than broadly removing reflection from the CLI. Extend migration documentation for JVM class generation and extension users.

Step → verify: existing extension tests, image conversion/avatar tests, Java examples, CLI tests and the existing native-image smoke paths work with the migrated library. Platform documentation identifies these modules accurately.

## Task 21

**Verify the complete publication and BOM graph.**

Finalize `faker-pub-conventions.gradle.kts`, `utils/Publishing.kt`, common provider conventions and `bom/build.gradle.kts` using actual core/domain artifacts. Account for separately versioned modules and expose core transitively wherever public inheritance requires it. Extend the production root-POM decision proven in Task 29 to the full module graph, preserving or documenting Maven/JVM coordinates. Compile representative consumers at both declared minimum Kotlin floors.

Maintain or rename the existing external-coordinate consumer harness, updating links. It contains no copied Faker implementation; candidate mechanism copies were retired when promoted, not repeatedly rebuilt for this task. Resolve every advertised variant from a project-local repository with no project substitution or accidental Maven Local fallback.

Step → verify: KMP common consumers, JVM Kotlin/Java and Maven/BOM consumers resolve the expected dependency graph and execute representative providers; all target artifacts referenced by metadata exist; excluded modules advertise JVM only.

## Task 22

**Enforce actual target coverage in CI.**

Extend the core target/coverage checks installed by Task 31 across all migrated modules and external consumers in `.github/workflows/pr.yml`, `master.yml` and `run_gradle.yml`. The current snapshot workflow selects only root and JVM publications; replace that Ubuntu publication matrix with one `macos-latest` publishing job using the Task 1-validated Xcode/runner combination. It is configured to publish root/JVM/JS/iOS/BOM coordinates once; Linux/Windows jobs verify only. The Task 21 graph result is necessary but not sufficient to open publishing: keep the guard closed until Task 24 and a subsequent authorized release action. Revalidate Apple device/simulator availability and signing on that host rather than relying on Linux Apple cross-compilation. Keep existing API, formatting, JVM host/minimum/maximum and CLI release requirements. Ensure filtering and final job aggregation cannot count an accidentally skipped target as successful KMP coverage.

Use type-checked common invocation cases instead of runtime reflection for cross-target provider smoke coverage. Retain focused unit tests for meaningful branches and failure modes. Extend the coverage measurement already installed by Task 31 to the full scope; disclose target instrumentation limitations rather than claiming unmeasured 100% coverage.

Step → verify: each required target produces named test evidence; intentionally failing one consumer or seeded vector fails the corresponding check; local commands and CI select the same source sets and versions. Avoid repeated broad runs without a new failure or unresolved risk.

## Task 23

**Publish the user-facing migration and contributor guidance.**

Update `README.md`, `CONTRIBUTING.md`, relevant `docs/pages/data-provider/core/` and `docs/pages/fakers/` pages, and the actual MkDocs navigation/includes. Add a next-major migration page covering common dependencies, supported targets/modules, class-generation configuration, date/locale/enum types, uniqueness configuration, regex syntax, UUID behavior and seeded guarantees. Include minimum consumer Kotlin/stdlib versions for JVM and KMP, removal of legacy locale resources from JVM JARs, and the difference between independent same-seed streams (including cross-family correlation) and a deliberately shared injected Random object.

Update provider authoring instructions for common sources, explicit factories, common tests and generated dictionaries. Correct outdated build/source paths encountered in those affected instructions; do not rewrite unrelated documentation. Mark all deliberate breaks against the checked API baseline, including consumer compiler floors and resource packaging as well as signatures.

Step → verify: documented examples are exercised by common/JVM consumer tests, supported modules match metadata, and the existing MkDocs build succeeds. No docs claim stability or target support that lacks evidence.

## Task 24

**Complete release verification and conformance review.**

Invoke `$kk:test` for the meaningful shared behaviors and required repository/release suites; `$kk:document` for changed public/contributor behavior; `$kk:review-code Kotlin` for the implementation; and `$kk:review-spec kotlin-multiplatform` against all design/implementation/task documents. Review generated artifacts and API changes, not only source compilation.

Sufficient evidence is the target behavior matrix, complete corpus/pattern coverage, equal seeded vectors with semantic assertions, published consumer results, coverage report, required existing checks and documented migration. Broader verification is justified by shared-runtime regressions, unsupported real inputs, publishing mismatches, unexpected target differences or changed toolchains. Do not use retries to dismiss reproducible failures.

Record unaddressed findings durably with the affected scope, reason and next action. Material violations of the agreed scope block closure. Move the completed feature documents to `docs/feat/done/kotlin-multiplatform/` and update links when the implementation is actually complete. Closing the GitHub issue or publishing a remote release is a separate authorized repository action, not an effect of this design task.

Step → verify: implementation acceptance has linked evidence and every task is complete; independent reviews have no unresolved material gaps. Keep upload/tag guards closed during this verification. Record the release handoff: after an authorized publication, the release owner repeats the external consumer check against remote coordinates before closing #226. Implementation completion is not a claim that publication or issue closure happened.

## Deferred decisions are explicit work

Exact producer/dependency pins, tested minimum consumer Kotlin floors, the full generation grammar, date return shape, JVM facade naming and locale tags outside the dictionary set remain assigned to Tasks 1/4/9/11/12, with real consumer confirmation in Tasks 29/21. Update the design with evidence before dependent work.

The old release-blocking exclusion-engine decision is retired: Task 7 retains Kotlin Regex and documents caller-dependent matching equivalence. Review history and the rationale remain in [review-remediation.md](review-remediation.md) and the historical fact-check report.
