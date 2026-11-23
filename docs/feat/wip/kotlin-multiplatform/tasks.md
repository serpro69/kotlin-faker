# Tasks: Kotlin Multiplatform support

> Design: [design.md](design.md)
> Implementation: [implementation.md](implementation.md)
> Verification: [verification.md](verification.md)
> Review remediation: [review-remediation.md](review-remediation.md)
> Issue: [#226](https://github.com/serpro69/kotlin-faker/issues/226)
> Status: in-progress
> Created: 2026-11-23
> Release: next major
> Not Doing: desktop Native/Wasm, portable class construction/images, locale splitting, remote dictionaries, full regex parity, npm/Swift SDKs, new concurrency/crypto guarantees, cross-release seeded stability

Tasks 1–4 plus Task 25 are the mechanism-only feasibility milestone. Task 26 adopts the production toolchain/targets and Task 31 enforces CI/coverage/publication controls before extraction. Task 30 closes common runtime/base declarations; Tasks 27/28 close core providers; Task 13 exposes the facade and Task 29 validates its publications. New task IDs preserve the old review references and are placed in dependency order.

Task 1 is complete; Task 2 is in progress and subsequent tasks remain pending behind the explicit feasibility/production gates. Size exemptions cover identical moves/registrations only, not new algorithms, generators, decoders, conventions or test families. The former exclusion-engine blocker is retired: Kotlin Regex remains, with equivalent caller-pattern behavior required for seeded equality. Dependencies below are technical prerequisites; shared wrapper-file edits need coordination without creating arbitrary correctness edges.

Target checks follow the design's explicit matrix: run JVM, browser, Node and the declared iOS simulator cases; compile/link the iOS device consumer. Do not treat a skipped or compile-only task as execution evidence.

## Task 1: Inventory inputs, test execution and consumer compatibility

- **Status:** done
- **Depends on:** —
- **Size:** M
- **Can run in parallel with:** —
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-1](implementation.md#task-1)

### Subtasks

- [x] 1.1 Inventory dictionary shapes, fallback cases, post-preprocessing regex inputs, locale acceptance, Java APIs and legacy resource consumers; record findings in `feasibility.md`.
- [x] 1.2 Select and verify the producer Kotlin/Gradle/Xcode/library tuple and explicit JVM/KMP minimum consumer Kotlin/stdlib floors; record upgrade impact and language/API settings.
- [x] 1.3 Initialize the minimal probe harness with kotlin.test common tests, JVM JUnit 5 integration and JS/Native runners; keep existing Kotest specs in jvmTest, without a new multiplatform Kotest/KSP runner.
- [x] 1.4 Record the compile/runtime matrix and exact task/runner names; prove common test discovery in each execution environment and device compilation/linking, inspect coverage instrumentation and record unavailable checks.

### Execution context

- **2026-11-23 — observed:** Started from clean revision `9a3844298b58c45d60e01d39cc72694163fcbdd1`. Scope is the standalone `test/fixtures/kmp-feasibility/` harness and input/toolchain evidence; production sources, versions and release configuration remain unchanged. Later mechanism and migration tasks remain pending.
- **2026-11-23 — explicitly accepted:** The user selected an existing GitHub Actions runner for Apple execution because the local host is Linux. Prepare an isolated feasibility branch/workflow to obtain actual simulator and device-link evidence.
- **2026-11-23 — observed:** Source inspection found no JaCoCo/Kover configuration or measured coverage gate. Task 1 will select and document measurement for Task 31; existing test success cannot establish 100% coverage.
- **2026-11-23 — observed:** [feasibility.md](feasibility.md) records the completed full-corpus inventory and two existing YAML Boolean coercions. Their explicit disposition belongs to Task 2; no source values were silently fixed or removed.
- **2026-11-23 — observed:** Local JVM/Node probes pass with producer Kotlin 2.3.21 and provisional consumer floor 2.3.20. The 2.2.21 attempt failed and is retained as evidence. Stdlib resolves to 2.3.20 for the lower compiler. Hosted run [45205602246](https://github.com/serpro69/kotlin-faker/actions/runs/45205602246) completed successfully: all five jobs passed, with 27 test cases across nine runtime/compiler configurations plus device linking for both compilers.
- **2026-11-23 — observed:** Isolated source reviewer inspected all nine changed files through `61c720914`, reported no source defect and withheld acceptance pending Apple evidence and the explicit stdlib declaration. The latter is now documented. External Claude review failed authentication before source inspection; no external-review coverage is claimed.
- **2026-11-23 — observed:** Producer Apple job 137800354997 remained unassigned while all other jobs passed. After confirming no job was running, cancelled the queued run and rescheduled that job alone. Successor 137808660696 passed in attempt 2; earlier successful jobs/reports are retained. No test failure was retried.
- **2026-11-23 — complete:** Final isolated reviewer approved Task 1 after inspecting both Apple XML reports, linking logs and documentation amendments; no findings or evidence requests remain. All four subtasks are complete. Kover JVM measurement is selected for installation in Task 31; production plugin adoption and published-artifact consumer checks remain assigned to Tasks 26 and 4/29/21 respectively. This completes only Task 1, not the mechanism feasibility milestone or a release.

## Task 2: Prove the full embedded payload and lazy decoding

- **Status:** in-progress
- **Depends on:** Task 1
- **Size:** M
- **Can run in parallel with:** Task 25
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-2](implementation.md#task-2)

### Subtasks

- [ ] 2.1 Resolve anchors and split locale-wide inputs into source/category projections with an overlay manifest; implement bounded suppliers and a minimal lazy accessor.
- [ ] 2.2 Keep providers, expressions, uniqueness and random generators out of this payload task.
- [ ] 2.3 Verify every source value survives projection; compile/access the full payload on all targets and prove one category lookup does not parse unrelated categories of a locale-wide file.
- [ ] 2.4 Record generated-source/artifact sizes, decoded-category counts and compilation costs in `feasibility.md`.

### Execution context

- **2026-11-23 — observed:** Started from clean `ae5373ab90129e0c8ddb5d5660b71ca4e931f7cf` on `feat/kmp_support`. Task 1 is complete. Scope is the existing standalone harness, its CI checks and evidence; no production provider/resolver/RNG or publication code is introduced.
- **2026-11-23 — selected:** Preserve the current SnakeYAML/Jackson conversion in this probe, including the `no.yml` parsed root key `false` and the Spanish Boolean scalar. Retain logical file locale separately in the manifest and prove exact document reconstruction; do not edit YAML or silently coerce values. Task 5 must explicitly decide any behavior correction before resolver integration.
- **2026-11-23 — approach:** Two ordered slices keep the work bounded: (1) generator, manifest/accessor and build wiring; (2) full-source/shared tests, workflow measurements and evidence. Generate bounded string suppliers under build outputs, with independent source/category decoding and explicit locale/source overlay metadata.

## Task 25: Prove seeded generation and sampling independently

- **Status:** pending
- **Depends on:** Task 1
- **Size:** M
- **Can run in parallel with:** Task 2
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-25](implementation.md#task-25)

### Subtasks

- [ ] 25.1 Implement the candidate raw seeded stream and explicit bounded-number/byte/float/shuffle operations in the minimal harness.
- [ ] 25.2 Specify seed mapping, overflow/bit rules, consumption order and independent reference vectors without provider or payload logic.
- [ ] 25.3 Execute mixed-operation, boundary and rejection cases on JVM, JS and iOS.
- [ ] 25.4 Record the sampling specification and evidence in `feasibility.md`; raw RNG agreement alone does not complete the task.

## Task 3: Prove the portable regex generation grammar

- **Status:** pending
- **Depends on:** Tasks 1, 25
- **Size:** M
- **Can run in parallel with:** Task 2
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-3](implementation.md#task-3)

### Subtasks

- [ ] 3.1 Use the actual post-preprocessing corpus and proven seeded stream to specify/implement the candidate regex parser and generator.
- [ ] 3.2 Do not add address fallback, book providers or uniqueness; those are production behavior.
- [ ] 3.3 Classify/support every bundled generation pattern and reject unsupported syntax before consuming randomness.
- [ ] 3.4 Run semantic matching invariants and equal seeded generation cases on all targets; record unresolved constructs as feasibility blockers.

## Task 4: Consume one minimal probe artifact and decide feasibility

- **Status:** pending
- **Depends on:** Tasks 2, 3, 25
- **Size:** M
- **Can run in parallel with:** —
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-4](implementation.md#task-4)

### Subtasks

- [ ] 4.1 Publish one minimal probe under distinct local-only coordinates, outside root includes/BOM/release aggregation; consume it independently at the declared compiler floors.
- [ ] 4.2 Run JVM, JS browser/Node and iOS simulator checks without copying Faker/provider code or emulating the production module graph.
- [ ] 4.3 Complete `feasibility.md` with measured payload costs, grammar, seeded specification, toolchain/test tuple and consumer floors.
- [ ] 4.4 Resolve genuine mechanism failures before production extraction; reserve production POM/signing/BOM validation for Tasks 29/21/22.

## Task 26: Enable production targets and common tests early

- **Status:** pending
- **Depends on:** Task 4
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** The mechanism decision precedes production wiring; every subsequent production slice uses these actual target runners.
- **Docs:** [implementation.md#task-26](implementation.md#task-26)

### Subtasks

- [ ] 26.1 Apply the validated tuple to the production catalog/wrapper/plugins and wire reusable portable targets; retain the JVM facade and verify existing JVM checks.
- [ ] 26.2 Scope JVM dependencies correctly and install the selected kotlin.test commonTest/JVM JUnit 5 integration while preserving existing JVM Kotest specs.
- [ ] 26.3 Define only closed common configuration/resource/random contracts and execute a real production common test on every target; later components may not reference legacy JVM facades.
- [ ] 26.4 Verify actual production common test discovery/execution on every target using the selected tuple; Task 31 must enforce those checks before extraction.

## Task 31: Enforce target checks, coverage and publication guards

- **Status:** pending
- **Depends on:** Task 26
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** Production behavior must enter with required target/coverage checks and no incomplete remote-release path.
- **Docs:** [implementation.md#task-31](implementation.md#task-31)

### Subtasks

- [ ] 31.1 Add required PR jobs for actual JVM/JS/iOS tests using Task 1 environments; select enabled portable modules as they opt in and require non-empty shared-test evidence.
- [ ] 31.2 Install the selected coverage measurement before new production behavior, recording instrumentation limits separately from target behavior.
- [ ] 31.3 Guard direct Maven and NMCP uploads, the root publish wrapper and release/tag entry points through Task 24; preserve project-local staging and prevent tagging after a skipped publish.
- [ ] 31.4 Test the actual guard configuration with inert upload/tag actions in an isolated build and controlled failing target/coverage cases; dry-run alone is not proof and no real upload/tag is performed.

## Task 5: Integrate dictionary resolution and remove legacy resources

- **Status:** pending
- **Depends on:** Task 31
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** Requires real production target/test wiring, not only the prototype's runners.
- **Docs:** [implementation.md#task-5](implementation.md#task-5)

### Subtasks

- [ ] 5.1 Promote internal generator/accessor code only; implement immutable projected/merged views keyed by locale, source/category/secondary-resource identity and overlay order, with JVM adapters.
- [ ] 5.2 Preserve overlays/aliases/special categories; characterize third-level localized merging, record the preserve/fix decision and update stale reflection-based resolver KDoc.
- [ ] 5.3 Remove main-artifact locales/** YAML/JSON at runtime cutover while retaining authoring/test inputs; adapt FakerIT enumeration to a generated locale manifest and explicit test-fixture lookup.
- [ ] 5.4 Verify cold/warm caches and reversed sibling loads with identical generation calls on every target; inspect the JAR for complete embedded data, no legacy locale files and no probe scaffolding.

## Task 6: Integrate controlled seeded primitive operations

- **Status:** pending
- **Depends on:** Tasks 5, 25
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** Uses the production resource/runtime boundary from Task 5 and the seeded specification from Task 25.
- **Docs:** [implementation.md#task-6](implementation.md#task-6)

### Subtasks

- [ ] 6.1 Promote the approved seeded stream/sampling and refactor runtime/configuration ownership so scalar seeds create independent same-start streams per Faker.
- [ ] 6.2 Route primitive operations and owned collection selection through common components used by the JVM adapters; common code cannot depend on legacy JVM interfaces.
- [ ] 6.3 Test/document same-seed cross-family correlation, intentionally shared injected Random state, interleaving, seed precedence and caller-input conditions; do not add per-family seed mixing.
- [ ] 6.4 Execute actual production common boundary/mixed-operation/cache-independence vectors on every Task 26 target and retain JVM facade checks.

## Task 7: Port uniqueness with Kotlin Regex exclusions

- **Status:** pending
- **Depends on:** Task 6
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-7](implementation.md#task-7)

### Subtasks

- [ ] 7.1 Replace reflective clone/return-type inspection with common state, identities and typed factories, including additional Internet/Dune/Finance dependencies.
- [ ] 7.2 Preserve provider/function global scopes, local precedence and characterized reset/wrapped-call behavior.
- [ ] 7.3 Keep Regex.containsMatchIn exclusions; document cross-target equivalent matching as a caller-input condition and bound every rejection path, including all-values-excluded cases.
- [ ] 7.4 Run actual common interleaving/reset/exclusion/exhaustion tests on all targets plus JVM adapter checks; no exclusion DSL or separate engine gate is introduced.

## Task 8: Integrate portable string and regex generation

- **Status:** pending
- **Depends on:** Tasks 5, 6
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** Requires resource/resolver and random contracts, not the uniqueness refactor. Coordinate shared adapter edits without a Task 7 edge.
- **Docs:** [implementation.md#task-8](implementation.md#task-8)

### Subtasks

- [ ] 8.1 Promote the Task 3 parser/generator into common production code and route JVM StringProvider/FakerService adapters through that exact implementation.
- [ ] 8.2 Preserve address/bank/finance/vehicle/cryptography preprocessing and specify any retained Regex-overload options.
- [ ] 8.3 Update grammar KDoc and remove runtime RgxGen after its final caller migrates.
- [ ] 8.4 Run actual common corpus/semantic/seeded/fail-loud tests on all targets; current uniqueness wrappers may remain until Task 7.

## Task 9: Isolate JVM class-generation APIs

- **Status:** pending
- **Depends on:** Task 6
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-9](implementation.md#task-9)

### Subtasks

- [ ] 9.1 Remove reflection-only configuration, define the JVM factory and move clean FakerConfig/builder declarations to common sources.
- [ ] 9.2 Keep `RandomClassProvider`, `ParameterInfo`, class-generation helpers and Java `FunctionalUtil` in JVM sources.
- [ ] 9.3 Adapt JVM callers to existing common primitives; retain Java date/locale bridges until Tasks 11/12 supply replacements, avoiding a dependency on future date APIs.
- [ ] 9.4 Verify class-construction/Java tests and absence of JVM-only types or throwing target stubs in the common contract.

## Task 10: Port UUID, collections and text utilities

- **Status:** pending
- **Depends on:** Task 6
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** Uses controlled random operations; it does not require regex generation or uniqueness. Coordinate shared random-wrapper edits.
- **Docs:** [implementation.md#task-10](implementation.md#task-10)

### Subtasks

- [ ] 10.1 Finish common UUID/collection sampling and shuffle components, specifying UUID version/format and ordering.
- [ ] 10.2 Replace JVM formatting and ambiguous owned text operations in Internet/helper paths while retaining explicit transliteration.
- [ ] 10.3 Audit money/formatting consumers for controlled randomness; keep JVM adapters calling the same common functions.
- [ ] 10.4 Execute actual production utility tests on all targets for UUID/range/order/MAC/Unicode invariants and equal vectors.

## Task 11: Port birth dates and random timestamps

- **Status:** pending
- **Depends on:** Tasks 6, 9
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** Needs the seeded contract and the isolated JVM facade for conversions, not the text/UUID task.
- **Docs:** [implementation.md#task-11](implementation.md#task-11)

### Subtasks

- [ ] 11.1 Record common date/timestamp return shapes, offsets, precision, bounds and clock defaults before changing signatures.
- [ ] 11.2 Implement common Person/date operations with explicit reference times and JVM adapters to those actual components.
- [ ] 11.3 Adapt the Task 9 JVM class-generation facade and conversion helpers, recording API-baseline/migration changes.
- [ ] 11.4 Execute actual common age/leap-day/bounds/offset cases with equal seeded outputs on all Task 26 targets.

## Task 12: Port locale-aware random strings

- **Status:** pending
- **Depends on:** Tasks 1, 6
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** Uses the locale inventory and common random contract; dates are unrelated. Coordinate shared RandomService/IRandom edits without a Task 11 edge.
- **Docs:** [implementation.md#task-12](implementation.md#task-12)

### Subtasks

- [ ] 12.1 Generate deterministic exemplar tables from pinned build-time ICU data, preserving character-option flags.
- [ ] 12.2 Settle tags outside bundled locales and empty-table behavior explicitly, recording accepted-input changes.
- [ ] 12.3 Implement common locale-string components and update JVM delegations, removing runtime ICU when its last consumer migrates.
- [ ] 12.4 Execute actual production locale/Unicode/options/seeded cases on all targets without consumer ICU.

## Task 30: Close common runtime and provider-base declarations

- **Status:** pending
- **Depends on:** Tasks 7, 8, 9, 10, 11, 12
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** Component algorithms and API decisions must be complete before their JVM facades/base signatures can become common.
- **Docs:** [implementation.md#task-30](implementation.md#task-30)

### Subtasks

- [ ] 30.1 Promote/audit AbstractFaker, FakerService, IRandom, RandomService and provider-base/uniqueness declarations; reuse completed components and the Task 9 common configuration.
- [ ] 30.2 Close their annotation/identifier/exception/helper dependencies and remove obsolete JVM forwarders, retaining only genuine Java conveniences.
- [ ] 30.3 Verify one runtime per owner and common constructor/property signatures without bringing the concrete Faker registry forward from Task 13.
- [ ] 30.4 Exercise actual runtime/base types on all targets and compile the still-JVM concrete providers against those same promoted declarations.

## Task 27: Close dictionary-only and template core providers

- **Status:** pending
- **Depends on:** Task 30
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** Task 30 must have made the actual runtime/base declarations common; component tests alone do not establish that.
- **Docs:** [implementation.md#task-27](implementation.md#task-27)

### Subtasks

- [ ] 27.1 Move common-ready Name/Color/Currency/File/Gender/IdNumber/Measurement/Source providers to common sources using the established runtime.
- [ ] 27.2 Move StringProvider/CryptographyProvider through the proven generator paths and adapt identical factory registrations.
- [ ] 27.3 Add typed invocation coverage and remaining provider-specific semantic assertions; no target convention or new runtime algorithm belongs in this task.
- [ ] 27.4 Run every listed provider path on all production targets; split any discovered bespoke behavior rather than treating it as a mechanical move.

## Task 28: Close composed and locale-special core providers

- **Status:** pending
- **Depends on:** Task 27
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** Composed providers use the common provider/runtime types closed by Task 27.
- **Docs:** [implementation.md#task-28](implementation.md#task-28)

### Subtasks

- [ ] 28.1 Move Address/Internet/Money/PhoneNumber/CurrencySymbol/Separator onto the tested common resolver and utility paths.
- [ ] 28.2 Wire provider composition such as Internet's Name reference and verify typed unique-factory dependencies.
- [ ] 28.3 Confirm Person/RandomProvider common signatures are ready for Faker; defer no missing provider type to Task 13.
- [ ] 28.4 Run actual common cross-category/phone/currency/money/Internet composition tests on every target.

## Task 13: Expose the common Faker facade

- **Status:** pending
- **Depends on:** Tasks 27, 28
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-13](implementation.md#task-13)

### Subtasks

- [ ] 13.1 Move only Faker and its builder/DSL facade once all property types and runtime dependencies are common.
- [ ] 13.2 Finalize public API baselines while retaining JVM-only helper/facade placement.
- [ ] 13.3 Add common consumer-style smoke cases for the complete Faker entry point, using already-tested providers.
- [ ] 13.4 Verify the facade on all production targets and existing JVM/Java checks; target setup and publication are not hidden in this task.

## Task 29: Consume actual core publications before domain migration

- **Status:** pending
- **Depends on:** Task 13
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** Requires the complete common facade; this is production publication evidence, unlike Task 4's minimal probe.
- **Docs:** [implementation.md#task-29](implementation.md#task-29)

### Subtasks

- [ ] 29.1 Stage actual core artifacts and prepare the shared opt-in domain convention: portable targets/tests, common api(core), and JVM-only testHelper; keep remote publication gated.
- [ ] 29.2 Point the external consumer harness at these coordinates, with no copied runtime/provider code or project substitution.
- [ ] 29.3 Compile/run JVM/KMP consumers at declared minimum Kotlin floors and verify Maven/JVM fallback separately from Gradle metadata discovery.
- [ ] 29.4 Verify root-POM behavior, resource removal and absence of probe APIs/scaffolding in JAR/klib/source/API surfaces; compile the domain convention and test actual core paths.

## Task 14: Port books and education providers

- **Status:** pending
- **Depends on:** Task 29
- **Size:** M
- **Can run in parallel with:** Tasks 15, 16, 17, 18, 19
- **Docs:** [implementation.md#task-14](implementation.md#task-14)

### Subtasks

- [ ] 14.1 Move `faker/books` and `faker/edu` implementations to common sources and expose core through common API dependencies.
- [ ] 14.2 Adapt nested education/book factories and additional constructor dependencies; preserve cross-category name lookup.
- [ ] 14.3 Add common invocation cases and focused nested/fallback/seeded tests for both modules.
- [ ] 14.4 Extend the actual-artifact consumer with books/education variants; the probe contains no domain implementation to retire.

## Task 15: Port commerce and technology providers

- **Status:** pending
- **Depends on:** Task 29
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
- **Depends on:** Task 29
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
- **Depends on:** Task 29
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
- **Depends on:** Task 29
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
- **Depends on:** Task 29
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
- [ ] 20.2 Update CLI introspection/native-image configuration; narrow the broad JSON resource include after legacy core locale removal while preserving other required JSON/image/reflection resources.
- [ ] 20.3 Verify existing JVM integration, avatar conversion and class-generation usage tests.
- [ ] 20.4 Run existing CLI tests/native-image smoke paths and record target availability in migration documentation.

## Task 21: Verify the complete publication and BOM graph

- **Status:** pending
- **Depends on:** Task 20
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-21](implementation.md#task-21)

### Subtasks

- [ ] 21.1 Extend the Task 29 production publication/root-POM evidence to the complete independently versioned core/domain/BOM graph.
- [ ] 21.2 Maintain/rename the external-coordinate consumer harness; it contains no duplicated Faker implementation.
- [ ] 21.3 Validate metadata discovery, target artifacts and transitive core APIs without project substitution or Maven Local fallback.
- [ ] 21.4 Run KMP and JVM Kotlin/Java/Maven consumers at declared compiler floors, checking runtime/stdlib resolution and JVM-only variant boundaries.

## Task 22: Enforce target execution and publication coverage in CI

- **Status:** pending
- **Depends on:** Task 21
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-22](implementation.md#task-22)

### Subtasks

- [ ] 22.1 Extend Task 31's existing required target checks across the full migrated module/consumer matrix and compiler-floor fixtures.
- [ ] 22.2 Prepare one validated macOS publisher for root/JVM/JS/iOS/BOM once each with signing; keep its remote gate closed through Task 24 and until a separate authorized release action.
- [ ] 22.3 Preserve API/formatting/JVM/CLI checks and extend Task 31's coverage enforcement to the complete scope with honest instrumentation limits.
- [ ] 22.4 Verify skipped/missing target jobs cannot report KMP success and each required check produces traceable evidence.

## Task 23: Document common usage and major-version migration

- **Status:** pending
- **Depends on:** Task 22
- **Size:** M
- **Can run in parallel with:** —
- **Docs:** [implementation.md#task-23](implementation.md#task-23)

### Subtasks

- [ ] 23.1 Update README, relevant MkDocs pages/navigation and tested common dependency examples.
- [ ] 23.2 Document minimum JVM/KMP consumer Kotlin/stdlib versions, removed locale resource paths, all API/platform boundaries, regex-generation syntax and caller-exclusion matching conditions.
- [ ] 23.3 Document same-seed cross-family correlation versus shared injected Random behavior; update CONTRIBUTING for kotlin.test common cases, retained JVM specs, factories and dictionaries.
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
- [ ] 24.5 Link implementation evidence and move completed docs to done only when complete; keep upload/tag gates closed and record the authorized-release/remote-consumer-check handoff before issue closure.

## Dependency Graph

```text
1 -> 2
1 -> 25
[1,25] -> 3
[2,3,25] -> 4 -> 26 -> 31 -> 5
[5,25] -> 6 -> 7
         6 -> 9
         6 -> 10
[5,6] -> 8
[6,9] -> 11
[1,6] -> 12
[7,8,9,10,11,12] -> 30 -> 27 -> 28
[27,28] -> 13 -> 29 -> 14
                29 -> 15
                29 -> 16
                29 -> 17
                29 -> 18
                29 -> 19
[14,15,16,17,18,19] -> 20 -> 21 -> 22 -> 23 -> 24
```

Square brackets denote prerequisites that must all complete. Per-task fields are authoritative. Shared-file coordination does not add correctness edges.
