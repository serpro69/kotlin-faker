# Consolidated design-review findings and remediation

Date: 2026-11-23. Reviewed baseline: `8bb69712b9bb07fbdf095b9462c5b7f4c8c8da55`. Inputs: the two design reviews supplied by the user. This pass independently read the current documents, repository configuration/runtime code, relevant official documentation and existing local JAR contents. It did not rerun an isolated review, compile KMP code or execute a prototype.

The overlapping production-target findings are consolidated as C1. The last review item contains two independent concerns, separated as C10 and C11. There are **11 consolidated findings: 10 valid documentation issues and one partly valid recommendation (C7)**. Every actionable part is addressed below. This closes findings against the plan; it does not mark implementation tasks complete.

## C1 — Production verification precedes target wiring

**Origin:** Review 1 RD-1; Review 2 P1 production-target timing. **Verdict: valid; fix required.**

The baseline implementation requires cross-target utility/date/locale tests in Tasks 10–12 but first declares production non-JVM targets in dependent Task 13. The probe explicitly uses copied implementations. [JVM conventions](../../../../buildSrc/src/main/kotlin/faker-jvm-conventions.gradle.kts) declare only JVM, and [shared conventions](../../../../buildSrc/src/main/kotlin/faker-kotlin-conventions.gradle.kts) broadly add JVM dependencies. This is a concrete missing execution path regardless of whether a particular single-target compiler happens to accept a JVM reference in common code. [Kotlin's target/source-set model](https://kotlinlang.org/docs/multiplatform/multiplatform-discover-project.html#targets) corroborates why declared targets matter.

**Fix:** New Task 26 enables actual core JVM/JS/iOS targets and common tests before Task 5. The legacy facade stays in `jvmMain`; each new common component receives common values/interfaces and is called by JVM adapters. Tasks 5–12 explicitly test the actual production components on all targets. Remote publication is gated separately, including root metadata, while local staging remains available. Task 13 only moves the ready facade. See [execution boundaries](implementation.md#execution-boundaries) and [Task 26](implementation.md#task-26).

## C2 — Shared tests have no selected framework

**Origin:** Review 2 P1 test framework. **Verdict: valid; fix required; numerical/rationale qualifications.**

The original plan names common tests without an execution framework. Repository conventions add `libs.bundles.test.kotest` only to `jvmTest` and call `useJUnitPlatform`; [the catalog](../../../../gradle/libs.versions.toml) pins Kotest 5.9.1 and its JUnit 5 runner. My source scan found 53 literal `DescribeSpec(...)` constructor occurrences in the inspected test source trees and 55 files referencing DescribeSpec; I did not corroborate “106 specs.” This does not change the missing-framework finding. Also, avoiding KSP solely for provider factories was not a blanket prohibition on KSP, so the alleged conflict was not itself decisive.

**Fix:** Select `kotlin.test` annotations/assertions in `commonTest`, with JVM JUnit 5 integration and KGP JS/Native execution. Keep existing Kotest specs in `jvmTest`; add shared behavior tests and selectively reuse fixtures/assertions, without mechanically moving DescribeSpec classes or introducing a multiplatform Kotest/KSP runner. Task 1 verifies actual discovery on every target; Task 26 installs it in production. [Official common-test guidance](https://kotlinlang.org/docs/multiplatform/multiplatform-run-tests.html) supports this route.

## C3 — The feasibility fixture duplicates too much behavior

**Origin:** Review 2 P2 fixture scope. **Verdict: valid scope/proportionality issue; fix required.**

Original Tasks 2–4 implement a decoder, resolver, named/address/book provider paths, unique factories, regex generation and a prototype module publication graph. Tasks 13/14/21 separately retire copies. Prototype publications cannot establish the behavior of production `utils/Publishing.kt`, signing or the actual dependency graph. Fallback and uniqueness still need cross-target tests, but with C1 resolved those tests can exercise production directly.

**Fix:** Restrict the probe to full payload compilation/lazy decoding (Task 2), seeded operations (Task 25), regex generation/corpus coverage (Task 3), and one minimal published probe/consumer (Task 4). No copied facade, provider, fallback resolver, domain artifact graph or uniqueness subsystem is created. Candidate components are promoted once, retiring their old probe copies at that handoff. Actual publishing is tested in Tasks 29/21/22, and fallback/uniqueness in production Tasks 5/7. This changes the investigation strategy, not the agreed release behavior.

## C4 — M-sized tasks hide multiple substantial implementations

**Origin:** Review 2 P2 task sizing. **Verdict: valid; fix required.**

The baseline Task 2 combines new build/generation/decoding/resolution/randomness, and Task 13 combines remaining provider logic, target conventions, common tests, the public facade and publishing. Those are separate substantive units, not repeated registrations. This conclusion follows from their explicit deliverables rather than a guessed future changed-file count.

**Fix:** Task 2 now owns payload only, Task 25 seeded operations and Task 3 regex generation. Original Task 13's responsibilities are split among Task 26 (targets/tests), Tasks 27/28 (simple/composed provider closure), Task 13 (facade) and Task 29 (actual publications). The size rule now expressly counts new generators/algorithms/decoders/conventions/test families as real work. IDs 1–24 are retained for traceability; IDs 25–29 are inserted in execution order.

## C5 — Minimum consumer Kotlin versions are not a release decision

**Origin:** Review 2 P2 consumer compiler floor. **Verdict: valid; fix required; compatibility rationale qualified.**

The baseline Task 1 chooses producer versions, while Task 23 omits consumer compiler requirements. Producer compiler, stdlib/public APIs and emitted metadata/klib formats constrain consumers. However, the exact oldest consumer compiler is not universally equal to the producer compiler: [official compatibility guidance](https://kotlinlang.org/docs/kotlin-evolution-principles.html#evolving-the-binary-format) distinguishes JVM binary handling from klib forward compatibility and discusses language/API settings.

**Fix:** Task 1 explicitly selects/tests minimum consumer Kotlin floors for JVM and KMP, resolved stdlib requirements and language/API settings, weighing any increase as a major-release cost. Probe consumers check provisional compatibility; Tasks 29/21 test real artifacts at the declared floors without silently upgrading consumer compilers. Task 23 publishes those requirements. No untested numeric minimum is invented in this remediation.

## C6 — Legacy locale resource packaging has no disposition

**Origin:** Review 2 P2 JVM YAML/JSON duplication. **Verdict: valid; fix required; native-image description refined.**

Source roots in [core build](../../../../core/build.gradle.kts) and [shared conventions](../../../../buildSrc/src/main/kotlin/faker-kotlin-conventions.gradle.kts) package authoring resources and generated JSON. Existing local `kotlin-faker-jvm-2.1.0-SNAPSHOT.jar` and `kotlin-faker-jvm-2.0.0-rc.13.jar` each contain 326 YAML and 326 JSON locale entries: 6,451,142 + 4,279,616 = 10,730,758 uncompressed bytes for those two representations. These are inspected local artifacts, not fresh builds or proof about all released versions.

The CLI [resource configuration](../../../../cli-bot/src/main/resources/META-INF/native-image/io.github.serpro69/cli-bot/resource-config.json) has a broad JSON include, not an explicit YAML/JSON locale inventory. A real internal consumer also exists: [FakerIT](../../../../core/src/jvmTest/kotlin/io/github/serpro69/kfaker/integration/FakerIT.kt) enumerates classpath YAML to select locales. Resource removal must adapt that test rather than silently eliminating its cases.

**Fix:** Task 5 removes main-artifact `locales/**` YAML/JSON when runtime lookup switches to embedded data, retains authoring and intentional test inputs, adds manifest-based locale enumeration and audits classloader consumers. Fresh-JAR inspection verifies both completeness and absence of duplicate representations. Task 20 audits/narrows CLI resource patterns without dropping required BLNS JSON/image/reflection resources; Task 23 documents the packaging break for direct resource consumers.

## C7 — Regex exclusions are over-scoped as an engine gate

**Origin:** Review 2 P2 exclusion portability gate. **Verdict: partly valid; simplify the gate, retain the real portability condition.**

The baseline already makes determinism conditional on supplied random objects/callbacks behaving identically. A second exclusion language or general matcher is not justified for existing caller-provided Regex values. However, the claim that ordinary regexes simply behave identically must not become a guarantee: [Kotlin Regex documentation](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.text/-regex/) explicitly identifies platform syntax/options differences and Native matching differences. The current [uniqueness wrapper](../../../../core/src/jvmMain/kotlin/io/github/serpro69/kfaker/provider/AbstractFakeDataProvider.kt) uses `containsMatchIn`.

**Fix:** Keep common Kotlin Regex exclusions and existing matching semantics. Document equivalent pattern/options matching on compared targets as a caller-input condition; test representative portable examples. Retire A7's release-blocking DSL/matcher decision and remove it from deferred gates. Preserve the independent, stronger portable-generation requirement for `regexify`, and preserve bounded exclusion retries. Historical statements in [verification.md](verification.md) remain identified as original findings, not current gates.

## C8 — Third-level merging and stale KDoc have no explicit subtasks

**Origin:** Review 2 P3 untracked design commitments. **Verdict: valid; fix required.**

The baseline design assigns these to Task 5 but its implementation/task checklist does not expressly require them. [FakerService](../../../../core/src/jvmMain/kotlin/io/github/serpro69/kfaker/FakerService.kt) documents incomplete third-level localized merging and still describes reflective provider-method dispatch despite dictionary-based resolution.

**Fix:** Task 5.2 and the implementation section now require production characterization cases, a recorded preserve-versus-scoped-fix decision, regression coverage for any correction, and updated resolver KDoc. This does not authorize an unrelated arbitrary-depth resolver rewrite.

## C9 — Serial dependencies lack technical justification

**Origin:** Review 2 P3 Tasks 8→7 and 12→11. **Verdict: valid; fix required.**

Regex generation needs the shared random/resolution contracts, not unique-view reconstruction; it can be called through the existing JVM adapter until that wrapper migrates. Locale character tables/string sampling do not use date generation. Shared wrapper files are an integration concern, not proof of a functional prerequisite.

**Fix:** Task 8 depends on Tasks 5/6; Task 12 on Tasks 1/6. Remove the unnecessary 10→8 and 11→10 chain as well, while retaining Task 11→9 because date changes adapt the isolated JVM class-generation facade. Dependency rationales and execution-boundary guidance distinguish technical prerequisites from file-edit coordination. Parallel markers promise only conflict-free scheduling, and the graph matches the fields.

## C10 — Apple publication host is not selected

**Origin:** Review 2 P3 smaller gap (a). **Verdict: valid; fix required.**

The current [snapshot workflow](../../../../.github/workflows/master.yml) publishes root/JVM artifacts on Ubuntu. The plan specifies a Mac for simulator execution but leaves the publication host implicit. Apple library cross-compilation varies with Kotlin versions; actual Apple test execution still needs an appropriate Mac/Xcode setup. [Kotlin publication documentation](https://kotlinlang.org/docs/multiplatform/multiplatform-publish-lib-setup.html#host-requirements) also cautions against duplicate publication.

**Fix:** Use one `macos-latest` publication job, with the Xcode/runner combination validated in Task 1, for all root/JVM/JS/iOS/BOM coordinates. Task 22 replaces the Ubuntu publication matrix; Linux/Windows remain verification-only. Remote publication opens only after complete graph checks. This avoids relying on Linux Apple cross-compilation and names the owner of root/BOM publication.

## C11 — Same-seed domain streams and injected shared state are not explained

**Origin:** Review 2 P3 smaller gap (b). **Verdict: valid documentation/contract gap; fix required, no automatic seed mixing needed.**

[FakerConfig.Builder.build](../../../../core/src/jvmMain/kotlin/io/github/serpro69/kfaker/FakerConfig.kt) currently creates/stores a Random object; [RandomService](../../../../core/src/jvmMain/kotlin/io/github/serpro69/kfaker/RandomService.kt) retains `config.random`. Thus reusing a configuration shares advancing state today. Independent per-Faker initialization from the same seed instead produces identical starting raw streams across families; output correlation is possible, although different call sequences/dictionaries do not necessarily return identical values. A reused caller Random object is still shared state.

**Fix:** Keep the explicitly chosen independent scalar-seed behavior and document identical starts/correlation across families, distinct seeds for separate streams, and intentionally shared injected state/interleaving. Retain seed precedence. Tasks 6 and 23 add behavioral tests and migration guidance for both modes. Do not add per-Faker seed derivation that changes the agreed model without a concrete need.

## Previous-pass validation and disposition

All C1–C11 actionable document changes are incorporated into design, implementation and task documents; the old source fact-check is retained as historical evidence. No implementation task has been marked done. The revised plan has 29 pending tasks and 117 unchecked subtasks.

Local checks passed for links/anchors, task/implementation ID and order agreement, required metadata, acyclic dependencies, parallel claims and the presence of Task 26 before every production extraction task. `git diff --check` passed. MkDocs remains unavailable (`mkdocs` is not on PATH); rendered-site validation is not claimed. Use the repository documentation environment and run `mkdocs build` to complete that check. No library build/test was needed or run for these Markdown-only changes.

That pass recommended a fresh sequencing review. The requested fresh read is recorded below; the earlier counts and checks above describe the 29-task revision.

## Fresh implementation-readiness pass

Re-read all five documents from disk, including the historical verification and the complete task graph, then checked the affected repository paths. No new skill or delegated review was invoked. C1–C11 remain addressed in the live documents. The following additional gaps were found and corrected before judging readiness:

| ID | Remaining issue and evidence | Correction and owner |
| --- | --- | --- |
| F1 | Tasks 5–12 extract common components, but no task explicitly promoted the actual runtime/service/base declarations assumed by Task 27. `YamlFakeDataProvider` still requires `FakerService`, which references `AbstractFaker`/`RandomService`; `IRandom` exposes configuration and Java types. Component tests alone do not close that signature graph. | Task 9 explicitly moves clean configuration. New Task 30 audits/promotes runtime/service/random/provider-base declarations and their helper types after the component/API work, before Task 27. It tests the actual common types while existing JVM providers compile against them. |
| F2 | Task 9 said to adapt class generation to portable date utilities even though those arrive in Task 11, which depends on Task 9. | Task 9 is structural/primitive separation and retains working Java date/locale bridges. Task 11 performs the later date conversion. The prerequisite direction is now executable without anticipating future APIs. |
| F3 | The proposed single runtime replaces separate provider caches. Current `FakerService.load` mutates a category-keyed dictionary, while siblings such as game providers select different secondary resource files. A category-only shared cache could conflate views. Whole-locale JSON decoding also conflicts with the promised category-lazy access. | Task 2 partitions locale-wide data into source/category projections after alias resolution and preserves overlay identity. Task 5 uses immutable resolved views keyed by the full locale/category/secondary-resource/overlay context and verifies cold/warm and reversed sibling loads. No source value may disappear during partitioning. |
| F4 | The smaller probe still needs public test entry points, but the plan did not explicitly keep its producer coordinates, API helpers and scaffolding out of root aggregation or promoted production artifacts. | Probe coordinates/settings stay local-only and outside root includes/BOM/aggregation. Only internal mechanism code/data is promoted; Tasks 5/29 inspect production packages/API surfaces for leakage. No copied Faker/provider implementation is added. |
| F5 | A trial tuple was selected without explicit ownership for applying it to production; CI/coverage enforcement was deferred to Task 22; parallel domain tasks lacked a pre-established common provider convention and a stated boundary for the JVM-only `testHelper` JAR. | Task 26 applies the tuple and target/test wiring. New Task 31 enforces target CI/coverage before extraction. Task 29 prepares the shared opt-in domain convention once, with common `api(core)` and JVM-only test helpers, before parallel migrations. Task 22 extends the existing enforcement. |
| F6 | The remote gate could open after graph verification (Task 21), before final docs/review (Tasks 23/24). The root build uploads through NMCP aggregation, and tagging depends on the publish wrapper; guarding or skipping only that wrapper is insufficient. A dry-run cannot prove execution-time guards. | Task 31 covers direct uploads, NMCP upload dependencies and tag/release entry points; the gate remains closed through Task 24. Task 22 only prepares the publisher. Inert upload/tag actions test the actual guard without side effects. An authorized release later verifies remote consumer coordinates before issue closure. |
| F7 | “Run on every target” was ambiguous between advertised iOS device/simulator artifacts and environments that CI can execute. | Added one compile/runtime matrix: JVM, browser, Node and declared iOS simulator execution; iOS device library/consumer compile-link evidence. Task 1 records exact task/runner names, and skipped compilation is never counted as runtime coverage. |

Repository evidence: [FakerService](../../../../core/src/jvmMain/kotlin/io/github/serpro69/kfaker/FakerService.kt), [provider base](../../../../core/src/jvmMain/kotlin/io/github/serpro69/kfaker/provider/YamlFakeDataProvider.kt), [IRandom](../../../../core/src/jvmMain/kotlin/io/github/serpro69/kfaker/IRandom.kt), [WorldOfWarcraft secondary resource](../../../../faker/games/src/jvmMain/kotlin/io/github/serpro69/kfaker/games/provider/WorldOfWarcraft.kt), [provider conventions](../../../../buildSrc/src/main/kotlin/faker-provider-conventions.gradle.kts), [test helper build](../../../../test/build.gradle.kts), [root publish/tag wiring](../../../../build.gradle.kts) and [publication conventions](../../../../buildSrc/src/main/kotlin/faker-pub-conventions.gradle.kts). These establish design constraints, not observed failures of an unimplemented migration.

### Earlier finding cross-check

| Findings | Live disposition after the fresh read |
| --- | --- |
| C1, C2 | Real production targets and kotlin.test are established before extraction; Task 31 now enforces their execution and coverage early. |
| C3, C4 | Probe scope stays limited to uncertain mechanisms; payload/RNG/regex, infrastructure, runtime closure, provider closure, facade and publication have explicit owners. |
| C5, C6 | Consumer compiler floors are tested with real artifacts; legacy resources are removed with locale-manifest and CLI-resource handling. |
| C7, C8 | Caller Regex keeps its documented equivalence condition; third-level merge characterization and stale KDoc updates remain explicit subtasks. |
| C9 | Technical dependencies are acyclic; regex/uniqueness and date/locale sequencing remain decoupled. F2 removes a hidden forward dependency from the prose. |
| C10, C11 | One tested Mac publication host is specified; seed correlation/shared-injection behavior is documented and tested. F6 strengthens final release readiness. |

### Readiness decision

**Ready to begin Task 1 and execute the staged plan.** No unresolved document inconsistency found in this pass requires another general design round before starting. The plan now contains 31 pending tasks and 125 unchecked subtasks; no implementation is marked complete.

Feasibility, the tested dependency/consumer tuple and stage-specific API choices still need execution evidence in their assigned tasks. They are explicit gates: production extraction follows the feasibility milestone, common provider moves follow Task 30's declaration closure, and broad domain migration follows real core artifact consumption. This is a readiness judgment about the plan, not a claim that the candidate implementation has passed tests or is release-ready.

Fresh-pass validation passed for all local links/anchors, task/implementation order and IDs, required metadata, all 48 prerequisite edges, acyclic dependencies, parallel claims, and early target/CI/runtime-closure prerequisites. All 31 tasks and 125 subtasks remain pending. `git diff --check` passed. MkDocs is still unavailable, so rendered-site validation remains unverified. Existing staged edits were preserved; this pass adds unstaged document corrections only.
