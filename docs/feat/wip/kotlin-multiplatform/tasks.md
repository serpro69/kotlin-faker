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

Tasks 1–4, 25 and 26 are complete; mechanism feasibility and production target setup are verified. Task 31 is next and must install required checks, coverage and publication guards before extraction. Size exemptions cover identical moves/registrations only, not new algorithms, generators, decoders, conventions or test families. The former exclusion-engine blocker is retired: Kotlin Regex remains, with equivalent caller-pattern behavior required for seeded equality. Dependencies below are technical prerequisites; shared wrapper-file edits need coordination without creating arbitrary correctness edges.

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

- **Status:** done
- **Depends on:** Task 1
- **Size:** M
- **Can run in parallel with:** Task 25
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-2](implementation.md#task-2)

### Subtasks

- [x] 2.1 Resolve anchors and split locale-wide inputs into source/category projections with an overlay manifest; implement bounded suppliers and a minimal lazy accessor.
- [x] 2.2 Keep providers, expressions, uniqueness and random generators out of this payload task.
- [x] 2.3 Verify every source value survives projection; compile/access the full payload on all targets and prove one category lookup does not parse unrelated categories of a locale-wide file.
- [x] 2.4 Record generated-source/artifact sizes, decoded-category counts and compilation costs in `feasibility.md`.

### Execution context

- **2026-11-23 — observed:** Started from clean `ae5373ab90129e0c8ddb5d5660b71ca4e931f7cf` on `feat/kmp_support`. Task 1 is complete. Scope is the existing standalone harness, its CI checks and evidence; no production provider/resolver/RNG or publication code is introduced.
- **2026-11-23 — selected:** Preserve the current SnakeYAML/Jackson conversion in this probe, including the `no.yml` parsed root key `false` and the Spanish Boolean scalar. Retain logical file locale separately in the manifest and prove exact document reconstruction; do not edit YAML or silently coerce values. Task 5 must explicitly decide any behavior correction before resolver integration.
- **2026-11-23 — approach:** Two ordered slices keep the work bounded: (1) generator, manifest/accessor and build wiring; (2) full-source/shared tests, workflow measurements and evidence. Generate bounded string suppliers under build outputs, with independent source/category decoding and explicit locale/source overlay metadata.
- **2026-11-23 — observed:** [Run 45214931092](https://github.com/serpro69/kotlin-faker/actions/runs/45214931092) passed all five jobs at `b9942303b`, with 75 non-skipped cases and both iOS device links. JVM reconstruction covers all 326 original documents; every target checks all 666 compiled projections against pre-compilation lengths/fingerprints and round-trips them. Generation is reproducible across hosts. [Measured sizes/costs](feasibility.md#hosted-verification-and-measured-costs) include accounting limits and decoded counts.
- **2026-11-23 — complete:** The external P2 fidelity-test gap was corrected using test-only expectations; P3 source-kind semantics and map-order follow-up were clarified. Task 5/15 carry direct links. Both independent reviewers approved scoped Task 2 after final evidence review, with no remaining acceptance blocker. Optional naming/timing notes, review coverage limits and future harness-owner actions are recorded in feasibility.md. This completes the payload feasibility task, not the remaining RNG/regex/publication milestone or PR merge readiness.
- **2026-11-23 — separate inherited failure:** The repository-wide macOS commerce integration test rejected literal `Lime #5`; the dictionary/provider/helper are unchanged from the Task 2 base. [Evidence and concrete remediation](feasibility.md#separate-inherited-repository-check-failure) are assigned to Task 15 / PR maintainer. The full PR check gate is failed; the five isolated payload jobs all passed. This unrelated assertion is not retried or weakened within Task 2.

## Task 25: Prove seeded generation and sampling independently

- **Status:** done
- **Depends on:** Task 1
- **Size:** M
- **Can run in parallel with:** Task 2
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-25](implementation.md#task-25)

### Subtasks

- [x] 25.1 Implement the candidate raw seeded stream and explicit bounded-number/byte/float/shuffle operations in the minimal harness.
- [x] 25.2 Specify seed mapping, overflow/bit rules, consumption order and independent reference vectors without provider or payload logic.
- [x] 25.3 Execute mixed-operation, boundary and rejection cases on JVM, JS and iOS.
- [x] 25.4 Record the sampling specification and evidence in `feasibility.md`; raw RNG agreement alone does not complete the task.

### Execution context

- **2026-11-23 — observed:** Started from clean `ab013f348532ce6a088fc2144aef0daa724f5ee8` on `feat/kmp_support`. Tasks 1/2 are complete. This task changes only the standalone mechanism harness, its existing target workflow and evidence; production random/provider behavior remains at the baseline.
- **2026-11-23 — approach:** Implement separate SplitMix64 and sampling components, with signed seed bit preservation, unsigned modulo-2^64 arithmetic, rejection-based bounds, fixed float/byte consumption and descending Fisher–Yates. Use independent arbitrary-precision reference vectors and scripted-word boundary tests. Reuse the Task 1/2 hosted matrix for Apple execution; local Linux cannot execute iOS. No new runtime dependency or production API is introduced.
- **2026-11-23 — execution slices:** Keep mechanism/reference/tests (four substantive files) separate from coverage/workflow wiring (two files) and documentation. The isolated fixture uses Gradle's built-in JaCoCo plugin with verified stable 0.8.16 for the two handwritten random classes; this does not install or alter Task 31's production Kover gate.
- **2026-11-23 — observed:** First capture `5f879df3-d86c-47ae-8809-a7ffcf68e483` passed 20 JVM tests and failed one of 19 Node tests: Kotlin/JS emitted the expected Float literal as `0.99999994`, whereas the sampler returned exact `1 - 2^-24`. Corrected the endpoint test to compare IEEE bits and the exact Double value; no sampler or valid assertion was weakened. All six mixed-operation reference vectors passed in that run.
- **2026-11-23 — explicitly accepted:** After initially requesting local commits only, the user explicitly authorized pushing to test iOS in CI (“you can push to test iOS in CI, that's fine”). This supersedes the earlier no-push instruction. Push the Task 25 candidate to the existing feature branch and run the prepared hosted matrix; no merge or release is authorized.
- **2026-11-23 — complete:** [Run 45223240006](https://github.com/serpro69/kotlin-faker/actions/runs/45223240006) passed all five jobs at `15f72144f61898aeb680161b40167035550e4800`: 174 executed cases across nine runtime/compiler configurations, including all 99 random-test executions, both device links and 100% measured JVM line/branch coverage. Both independent reviewers accepted the final source/evidence with no open findings or evidence requests. Review receipt/attribution limits are recorded in feasibility.md. This completes Task 25 only; Task 3 may now start, while production migration/publication and the inherited commerce assertion remain separately scoped.

## Task 3: Prove the portable regex generation grammar

- **Status:** done
- **Depends on:** Tasks 1, 25
- **Size:** M
- **Can run in parallel with:** Task 2
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-3](implementation.md#task-3)

### Subtasks

- [x] 3.1 Use the actual post-preprocessing corpus and proven seeded stream to specify/implement the candidate regex parser and generator.
- [x] 3.2 Do not add address fallback, book providers or uniqueness; those are production behavior.
- [x] 3.3 Classify/support every bundled generation pattern and reject unsupported syntax before consuming randomness.
- [x] 3.4 Run semantic matching invariants and equal seeded generation cases on all targets; record unresolved constructs as feasibility blockers.

### Execution context

- **2026-11-23 — observed:** Started from clean `121a30e1c` after Task 25's completed target matrix and independent reviews. User explicitly requested this one additional task and then a stop. Scope remains the standalone harness, its corpus tests, coverage/workflow wiring and evidence; production preprocessing/provider behavior is unchanged.
- **2026-11-23 — observed:** Task 1 inventory has 122,923 occurrences / 122,872 distinct patterns: 312 distinct syntax patterns and 122,560 distinct literals. The four existing FakerService regex regressions add anchors, dot, `\S`, `*` and `+`; cryptography adds six fixed hexadecimal lengths. Slashes and finance `L` are literal input characters, as recorded in the inventory.
- **2026-11-23 — approach:** First implement the bounded parser/AST and generator with focused semantic/seeded tests and an offline reference script (four substantive files). Then add common full-corpus and JVM inventory-comparison tests plus build/workflow wiring (four files). Reuse Task 2's embedded projections for test inputs and Task 25's sampler for generation; introduce no provider, resolver or new runtime dependency.
- **2026-11-23 — observed:** Focused JVM grammar checks passed (`067e1ea0-ed27-48b0-802b-c452385fb3b8`). The first full run (`49fec876-d7e4-4eaf-82a3-9f07be699b8b`) passed all 30 JVM tests and the full Node corpus, but Node's matcher rejected `a` for `(|a)()`. Inspection of the compiled Kotlin 2.3.21 JS stdlib showed `matches` checks the first unanchored `RegExp.exec` match's extent. The independent matcher now wraps the entire pattern in explicit anchors, requiring backtracking to a complete match. Generator behavior and the empty-alternative requirement are unchanged; both targets already produced corpus fingerprint `61af7582c14d505d`.
- **2026-11-23 — observed:** Final local capture `70fc55c9-6b10-483c-a32e-951010f9139d` passed 30 JVM, 28 Node and 28 browser cases plus both coverage gates. Regex measurement is 165/165 lines, 197/197 branches, 1,444/1,444 instructions; random measurement remains 46/46 lines and 32/32 branches. The parser also rejects excessive expansion work from nested empty repetitions before any draw. Hosted matrix and independent review remain pending.
- **2026-11-23 — observed:** [Run 45228848073](https://github.com/serpro69/kotlin-faker/actions/runs/45228848073) passed all five jobs at `95f89b358e930d1f918f01cc876eac0ffc773389`, with 258 non-skipped cases across nine configurations, both device links, complete corpus matching/equality and both 100% JVM coverage gates. Downloaded XML and Apple logs confirm the result. Final independent evidence review is pending.
- **2026-11-23 — review corrections:** External P3 feedback led to explicit construct-start diagnostics, reason-specific/isolated limit checks, no-draw verification for every literal, deterministic shorthand endpoint checks and inventory uniqueness assertions. Local correction verification passed all 86 cases plus 100% coverage (regex 168/168 lines, 197/197 branches). The requested construct audit and inherited production-promotion follow-up are recorded in feasibility.md; final corrected-source target checks and review remain pending.
- **2026-11-23 — complete:** Final correction [run 45231592739](https://github.com/serpro69/kotlin-faker/actions/runs/45231592739) passed all five jobs at `d53cfaae56ffd98a265265b7950049e6ac6d2ce6`, with 258 successful cases, both device links and complete measured coverage. The nine common regex cases, strengthened within their existing test methods, ran in all nine configurations. Both independent reviewers approved the corrected source and evidence; no acceptance blocker or evidence request remains. Optional production-promotion test hardening is recorded with Task 8 / Task 31 owners in feasibility.md. Task 3 is complete; stop here per the user's requested scope, leaving Task 4 pending.

## Task 4: Consume one minimal probe artifact and decide feasibility

- **Status:** done
- **Depends on:** Tasks 2, 3, 25
- **Size:** M
- **Can run in parallel with:** —
- **Strategy:** Risk-First
- **Docs:** [implementation.md#task-4](implementation.md#task-4)

### Subtasks

- [x] 4.1 Publish one minimal probe under distinct local-only coordinates, outside root includes/BOM/release aggregation; consume it independently at the declared compiler floors.
- [x] 4.2 Run JVM, JS browser/Node and iOS simulator checks without copying Faker/provider code or emulating the production module graph.
- [x] 4.3 Complete `feasibility.md` with measured payload costs, grammar, seeded specification, toolchain/test tuple and consumer floors.
- [x] 4.4 Resolve genuine mechanism failures before production extraction; reserve production POM/signing/BOM validation for Tasks 29/21/22.

### Execution context

- **2026-11-23 — observed:** Started from clean `ee1434410eb9e482c5a569e0358eb248a205b964` on `feat/kmp_support`. Tasks 2/3/25 and their hosted matrices are complete. Scope is the standalone probe, an independent coordinate consumer, the existing feasibility workflow and evidence; production configuration and mechanisms remain unchanged.
- **2026-11-23 — approach:** First add local publication, a primitive-valued probe boundary and an independent Kotlin 2.3.20 consumer (five substantive files). Then wire hosted verification and evidence. Stage producer Kotlin 2.3.21 artifacts into a file repository; prohibit Maven Local fallback and project substitution. Verify every payload projection, lazy decoding, seeded sampling and regex semantics on JVM 11/21, browser, Node and iOS simulator, with device linking. Record actual transitive stdlib resolution separately from compiler floors.
- **2026-11-23 — authorization:** Reuse the earlier explicit permission recorded under Task 25 to push this feature branch for hosted iOS verification. This authorizes CI execution, not a merge, remote artifact release or production migration.
- **2026-11-23 — observed:** Capture `9310cf13-1217-47a2-88ee-c5c224e1868d` failed common metadata transformation because the Linux staging subset lacked Apple publications. Run that explicit check on macOS against the complete staged repository; Linux still compiles the actual consumer common source for JVM/JS. Final local capture `0497e43f-519a-487a-992b-10bac0201da3` passed all 15 JVM/Node/browser cases and the boundary coverage gate. Hosted minimum-JDK/Apple checks and independent review remain pending.
- **2026-11-23 — observed:** [Run 45241697525](https://github.com/serpro69/kotlin-faker/actions/runs/45241697525) passed all eight jobs at `9b7ba49b42e5f98f2a6ef4245761e4fea4fecd2e`: 283 successful cases, including 25 independent artifact-consumer executions; common metadata compilation and all three device links executed. The published boundary has 17/17 JVM lines covered and no branch instructions. The tested consumer compiler floor is 2.3.20; the published JVM/JS stdlib requirement is 2.3.21. Full measurements and the feasible representation decision are in feasibility.md. Independent final evidence review remains pending.
- **2026-11-23 — complete:** Both independent reviewers approved the final source/evidence and documentation corrections, with no remaining finding or essential evidence request. The reproducible fingerprint command, source of seeded vectors, publication sizes/costs, review limits and inherited dictionary-trigger follow-up are recorded in feasibility.md/README. Task 4 and the mechanism feasibility milestone are complete. Stop after this requested task; Task 26 is next, and production publication/release remain separately gated.

## Task 26: Enable production targets and common tests early

- **Status:** done
- **Depends on:** Task 4
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** The mechanism decision precedes production wiring; every subsequent production slice uses these actual target runners.
- **Docs:** [implementation.md#task-26](implementation.md#task-26)

### Subtasks

- [x] 26.1 Apply the validated tuple to the production catalog/wrapper/plugins and wire reusable portable targets; retain the JVM facade and verify existing JVM checks.
- [x] 26.2 Scope JVM dependencies correctly and install the selected kotlin.test commonTest/JVM JUnit 5 integration while preserving existing JVM Kotest specs.
- [x] 26.3 Define only closed common configuration/resource/random contracts and execute a real production common test on every target; later components may not reference legacy JVM facades.
- [x] 26.4 Verify actual production common test discovery/execution on every target using the selected tuple; Task 31 must enforce those checks before extraction.

### Execution context

- **2026-11-23 — observed:** Started from clean `66d8752afc046f4fd185ae489a6d17130f1a2348` on `feat/kmp_support`, after Task 4's completed mechanism gate. The production baseline uses Kotlin 2.1.21 / Gradle 8.14.3, JVM 11/21 test launchers and compiler JDK 21; core has only JVM sources. Capture `ad033c5f-d95b-4e1b-9f7a-a2c138a5decb` completed core JVM minimum/maximum checks and repository API checks successfully before changes.
- **2026-11-23 — approach:** Bounded slices: validate the producer compiler with existing JVM/API tooling; stabilize the two failing JVM documentation cases; scope JVM dependencies/resources and add the reusable portable convention; add minimal internal common configuration/resource/random contracts, shared discovery tests and hosted execution evidence. Preserve the legacy JVM facade/provider behavior and existing Kotest specs. No resolver, seeded algorithm, provider migration or production publication is part of this task.
- **2026-11-23 — authorization:** Existing permission to push this feature branch for hosted iOS verification remains applicable. Task 26 verifies production targets; required CI/coverage/publication guard enforcement remains Task 31 and must precede extraction.
- **2026-11-23 — observed:** The application Kotlin BOM forced 2.3 metadata into Gradle 8.14.3's embedded build-logic compiler. Temporary compiler/language/frontend overrides exposed further script incompatibilities and were removed. The final correction removes that BOM from buildSrc, retaining Gradle's embedded compiler while using KGP 2.3.21 for production. Configuration succeeds without custom compiler flags (`8093fcb9-28e5-48e7-a52d-a6e34b0c4081`). The API baseline records seven additive synthetic IRandom dispatchers; existing DefaultImpls signatures remain.
- **2026-11-23 — observed:** Verification exposed two inherited probabilistic documentation assertions: PICK_RANDOMLY assumed mixed choices, and clearAll assumed independently sampled batches must overlap. Tests now use explicit choices/replayed draws and stronger exact assertions; production behavior is unchanged. Restoring generated resources specifically to jvmTest preserved all three locale cases lost during initial resource scoping. Node/Yarn downloads needed module-scoped Ivy repositories under the existing PREFER_SETTINGS policy.
- **2026-11-23 — observed:** Capture `3849fb60-4440-407d-aa93-c9d47a5bd4e2` passed core JVM 11/21, Node/browser, common metadata compilation, repository API and formatting checks. Both JVM reports contain 4,423 cases: all 4,415 baseline cases, three common cases and five existing Java cases newly discovered through Jupiter. Each JS runner executed all three common cases. Local coverage from `00bf29bc-de9c-4829-9901-e913824c0b8f` covers all 15 instructions/3 methods/1 line in GenerationConfiguration; the interfaces contain no executable bodies. Hosted production execution, tooling checks and final review remain pending.
- **2026-11-23 — observed:** [Run 45250478318](https://github.com/serpro69/kotlin-faker/actions/runs/45250478318) passed both jobs at `57156de08bd7f8d9fde6d6be6e18293d73b876de`: 16,333 passing cases with zero skips, including 15 common-case executions across JVM 11/21, browser, Node and iOS. Device linking and common metadata compilation executed. Repository-wide JVM/API/formatting checks passed; local core Dokka and six CLI cases also passed. Native-image, signing and publication-readiness claims remain outside Task 26. Final independent evidence review is pending.
- **2026-11-23 — verification corrections:** The separate existing PR run `45250494766` passed API/docs/native-image checks but exposed inherited assertion failures on all three hosts: a shuffled subset may legitimately stay sorted; separately sampled name batches need not overlap after disableAll; `Wagga Wagga` is valid literal data. Fixed the shuffle checks with controlled draws and exact flag-dependent results, disableAll with a repeated draw, and the existing literal exceptions for Wagga Wagga and the previously recorded Lime #5. No production generator or dictionary changed. Capture `30f569e5-ca8b-4509-86cd-840587fd1f0a` passed focused JVM 11/21, travel, JS and formatting checks. External P3 feedback also led to explicit Faker initialization in the Markdown snippets and exclusive Node/Yarn repository routing. Corrected-source hosted checks and review remain pending.
- **2026-11-23 — observed:** Corrected candidate `263938beb6481fce4cd368f559e073cb7869b374` passed [production run 45254467042](https://github.com/serpro69/kotlin-faker/actions/runs/45254467042) (16,333 cases, zero skips) and [the complete PR workflow 45254478142](https://github.com/serpro69/kotlin-faker/actions/runs/45254478142), including all three host checks and native-image smoke tests. The source fixes and documentation clarifications are verified; final evidence-review closure remains pending.
- **2026-11-23 — complete:** Both independent reviewers approved the corrected source and final evidence, with no outstanding finding or essential evidence request. Task 26 is complete; stop after this requested task. Task 31 is next and remains a prerequisite for extraction. The exact literal exception is implemented, while Task 15 still owns deterministic literal/unresolved-placeholder coverage. Review receipt limits and PR synthetic-merge provenance are recorded in feasibility.md; no release or remote publication readiness is claimed.

## Task 31: Enforce target checks, coverage and publication guards

- **Status:** in-progress
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

### Execution context

- **2026-11-23 — observed:** Started from clean `28a2aba03457afad5268cc997b24abf9422ca3d4` on `feat/kmp_support`. Task 26 is complete. The current PR aggregator accepts skipped jobs, production target evidence is a separate push workflow, and Maven file staging inherits dev/snapshot filters. Baseline configuration passed in capture `542d3246-76e6-4d81-b529-72eba60521bb`.
- **2026-11-23 — approach:** Work in bounded slices: publication guard and inert-action integration checks; Kover measurement and shared-source coverage verification; required target workflows and evidence checks. Preserve JVM/provider behavior and API baselines. Enable checks for every module applying the portable convention. Keep the remote gate unconditionally closed; reopening it requires Task 24 completion and a separately authorized release change.
- **2026-11-23 — verification scope:** Exercise actual configuration with inert uploads/tags, confirm real file staging, and inject controlled test/coverage failures in isolated copies. Reuse the recorded permission to push this feature branch for hosted Apple verification; no remote artifact upload, tag, merge or release is authorized. JVM coverage measures shared handwritten production sources only; browser/iOS execution remains separate evidence.
- **2026-11-23 — observed:** Production `kmpCoverageCheck` passed with 4,423 tests on each JVM and 1/1 measured common lines (no executable branches), capture `ad16ca5f-e685-4fc4-8e73-22ec7d056afa`. Node/browser, repository API and formatting checks passed in `1207e9db-e740-44f5-b7c1-75ec848ec617`. Evidence-verifier negative checks passed in `c12e38dd-7db3-4a7b-bcf0-37d6102d9db6`.
- **2026-11-23 — review correction:** The isolated reviewer found that one grouped upload/tag rejection could mask a missing direct-upload predicate. Strengthened the test to check every inventoried rejected path and invoke direct Maven and both NMCP upload types separately. Corrected-source execution and final evidence review remain pending; this finding is not treated as closed from the author edit alone.
- **2026-11-23 — inherited assertion correction:** Control capture `60b6cde3-a0ba-4a80-ae37-f60316e5d6ed` verified all registered guard paths, skipped-upload/tag, configuration-cache attempts and actual dev/snapshot staging, then stopped on the existing FakerServiceTest assertion rejecting `The Hon. Burton Jacobi`. The English name dictionary permits the two-word prefix and the adjacent recursive-name test already permits four words. Aligned the cross-category author assertion with that data contract; no production data or resolver behavior changed. This failure is retained, not retried unchanged.
- **2026-11-23 — observed:** Corrected guard capture `830df925-935f-4518-88e2-7969977a711d` passed the three isolated direct uploads, all-path/cache/tag checks and both staging versions. Its coverage-control setup then hit an inherited probabilistic assertion in `RandomClassProviderTest.kt:617` (five generated elements contained no configured `foo`). Negative controls now select common tests, which exercise the measured code; normal production checks still run the full JVM suites. **Deferred owner: Task 9 implementer** — make that collection-choice test deterministic with controlled draws and verify both nullable/custom-generation paths. The failure log remains under `build/kmp-controls/evidence/coverage-4zqli94t/` and in the capture; no retry of the unchanged failing control is claimed as evidence.
- **2026-11-23 — observed:** Effective GitHub rules for `master` require `Final PR results` and CodeQL (ruleset 4466739); classic branch-protection endpoint returns 404 because enforcement is via a ruleset. The final PR job now requires selected KMP and dictionary-fidelity jobs to succeed. The unchanged master snapshot publishing job intentionally fails closed at the migration guard until Task 24 and a separately authorized release change.

## Task 5: Integrate dictionary resolution and remove legacy resources

- **Status:** pending
- **Depends on:** Task 31
- **Size:** M
- **Can run in parallel with:** —
- **Dependency rationale:** Requires real production target/test wiring, not only the prototype's runners.
- **Docs:** [implementation.md#task-5](implementation.md#task-5)

### Subtasks

- [ ] 5.1 Promote internal generator/accessor code only; implement immutable projected/merged views keyed by locale, source/category/secondary-resource identity and overlay order, with JVM adapters.
- [ ] 5.2 Preserve overlays/aliases/special categories; characterize third-level localized merging, record the preserve/fix decision and update stale reflection-based resolver KDoc. Audit map sampling/rendering and English secondary-source selection per [Task 2's map-order follow-up](feasibility.md#migration-follow-up-map-order-and-iban-extraction).
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
- [ ] 15.2 Adapt Finance/Bank/Company/Vehicle behavior to the common utilities without losing argument/preprocessing semantics. Replace IBAN last-field string parsing with named `bban_pattern` access per [Task 2's map-order follow-up](feasibility.md#migration-follow-up-map-order-and-iban-extraction).
- [ ] 15.3 Cover every shared function through typed cases and focused banking/card/plate pattern tests. Retain Task 26's `Lime #5` literal exception and add deterministic coverage of that literal and a genuinely unresolved placeholder per [the recorded CI failure](feasibility.md#separate-inherited-repository-check-failure).
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
