# Isolated fact-check of the KMP design

Date: 2026-11-23. Method: `kk:chain-of-verification:isolated`, explicitly requested by the user. Four independent general-purpose agents received individual factual questions with no conversation history or initial design. They were instructed not to read this feature directory, edit files or run builds. All four completed with source citations; no fallback to non-isolated verification was needed.

This report verifies selected factual foundations of [design.md](design.md). It is not a design review, architecture conformance review, compilation result or proof of the proposed implementation. All implementation tasks remain pending.

## Initial answer

The initial draft made four sets of factual claims, with explicit feasibility limits:

1. The repository already uses the KMP plugin but configures only JVM implementation/targets. Runtime blockers include classloader/Jackson loading, ICU/Java utilities and reflection in uniqueness/class construction. Dictionary expressions already resolve other categories through data lookup. Core owns the locale corpus; pictures and existing extensions have JVM-specific behavior/builds.
2. Kotlin's seeded random API limits sequence stability to a Kotlin runtime version. A stronger Faker-version guarantee needs controlled generator, sampling, ordering and transformation behavior. SplitMix64 is a candidate raw generator, not proof of whole-library reproducibility.
3. Serialization JSON trees and common date APIs are plausible replacements. Documentation was inspected for serialization 1.11.0 and datetime 0.8.0; compatibility with the repository's Kotlin 2.1.21 / Gradle 8.14.3 baseline was not asserted. Public date return shapes and exact pins remained open.
4. The root Maven POM currently redirects to the JVM publication, while KMP uses root/target metadata. Consumer tests must establish whether that setup can remain. Domain modules currently have a JVM-only compile-time core dependency; real JS/iOS target declarations, artifacts and execution are still needed.

## Verification questions and independent answers

### Q1: What blocks common use, and who owns dictionaries and uniqueness today?

Agent: `verify_runtime` (general-purpose). Status: completed. Evidence: repository source only.

The plugin/target distinction, JVM dependencies, data-based cross-category resolution and remaining reflection were confirmed. All production locale dictionaries are under core, including domain data. Pictures has separate JPEG resources and AWT/ImageIO conversion; BLNS has its own JSON/text resources; the Kotest implementation is a small callback-to-`Arb` adapter even though its build is JVM-only.

Additional migration-relevant details:

- `AbstractFaker.fakerService` creates a new service on every getter access. Providers normally have separate dictionary caches while sharing the configured random object. One runtime per Faker is a proposed ownership change.
- Local uniqueness takes precedence over global configuration. Global state is partitioned by provider and key, not one cross-provider deduplication pool.
- `.unique` applies through wrapped resolver calls; direct methods such as IP generation are not automatically covered. Local/global reset behavior differs.
- The reflective clone passes one constructor argument, while Internet and Dune require additional arguments. This is a source-level mismatch, not an executed failure report.
- Exclusion rejection branches can recurse before the duplicate retry-limit checks. Bounded rejection behavior must be verified and corrected during the uniqueness migration.
- Resolver comments describing reflective invocation are stale; third-level localized merging is explicitly documented as limited.

Sources: [AbstractFaker](../../../../core/src/jvmMain/kotlin/io/github/serpro69/kfaker/AbstractFaker.kt), [FakerService](../../../../core/src/jvmMain/kotlin/io/github/serpro69/kfaker/FakerService.kt), [uniqueness delegate](../../../../core/src/jvmMain/kotlin/io/github/serpro69/kfaker/provider/unique/LocalUniqueDataProvider.kt), [base provider rejection paths](../../../../core/src/jvmMain/kotlin/io/github/serpro69/kfaker/provider/AbstractFakeDataProvider.kt), [global configuration](../../../../core/src/jvmMain/kotlin/io/github/serpro69/kfaker/provider/unique/UniqueProviderConfiguration.kt), [pictures](../../../../faker/pictures/src/jvmMain/kotlin/io/github/serpro69/kfaker/pictures/PicturesFaker.kt), [BLNS](../../../../extension/blns/src/jvmMain/kotlin/io/github/serpro69/kfaker/blns/Blns.kt), [Kotest adapter](../../../../extension/kotest-property/src/jvmMain/kotlin/io/github/serpro69/kfaker/kotest/ArbExtensions.kt).

### Q2: What does seeded Random guarantee, and what is the linked generator?

Agent: `verify_random` (general-purpose). Status: completed. Evidence: official API and versioned implementation sources.

The Kotlin API promises same-seed repetition within a Kotlin runtime version and permits future algorithm changes. This must not be misread as evidence that every target uses a different seeded algorithm: Kotlin 2.2.0's seeded factories use common XorWow code, while its default unseeded generator is platform-based.

The stronger Faker contract requires stable seed mapping, bounded sampling, byte/floating conversion, shuffle, data ordering and downstream transformations. Overriding only a raw-bit method while inheriting unspecified derived operations is insufficient. Caller-provided random objects, callbacks and unordered inputs need explicit conditions. JS exports would additionally require preservation of 64-bit seed precision, but an independent JS-export product is outside the current interpretation of scope.

The linked Vigna reference is the fixed-increment SplitMix64 generator. It is a plausible small raw-bit candidate. Choosing it does not validate bounded sampling, output distribution of each provider or the end-to-end promise.

Sources: [Random API](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.random/-random.html), [Kotlin 2.2.0 Random implementation](https://github.com/JetBrains/kotlin/blob/v2.2.0/libraries/stdlib/src/kotlin/random/Random.kt), [XorWow implementation](https://github.com/JetBrains/kotlin/blob/v2.2.0/libraries/stdlib/src/kotlin/random/XorWowRandom.kt), [common collection operations](https://github.com/JetBrains/kotlin/blob/v2.2.0/libraries/stdlib/common/src/generated/_Collections.kt), [SplitMix reference](https://prng.di.unimi.it/splitmix.c).

### Q3: What do the selected JSON/date versions actually support?

Agent: `verify_dependencies` (general-purpose). Status: completed. Evidence: tagged official source and release documentation.

Both libraries declare JVM, JS and Native targets, including iOS. Serialization 1.11.0 is built against Kotlin 2.3.20; this does not by itself prove that 2.3.20 is its exact minimum consumer compiler. Its compiler-plugin compatibility manifest is a different mechanism and cannot establish the entire consumer dependency graph's compatibility.

JSON-tree parsing uses a library-provided serializer and does not require applying the serialization compiler plugin to the consumer. Datetime 0.8.0 documents compatibility with Kotlin stdlib 2.1.20 or newer. Its instant family uses `kotlin.time.Instant`, which is experimental in Kotlin 2.1.21; `LocalDate`, `UtcOffset` and `FixedOffsetTimeZone` are available, but there is no direct `OffsetDateTime` value type. A parsing container is not a substitute that automatically preserves its value semantics.

The official Kotlin compatibility table gives KGP 2.1.20–2.1.21 a fully supported Gradle range ending at 8.12.1. The repository's 8.14.3 wrapper exceeds that range. That is a validation caveat, not evidence of a broken build. A supported, tested tuple remains a feasibility task.

Sources: [serialization release](https://github.com/Kotlin/kotlinx.serialization/releases/tag/v1.11.0), [tagged JSON parser](https://github.com/Kotlin/kotlinx.serialization/blob/v1.11.0/formats/json/commonMain/src/kotlinx/serialization/json/Json.kt), [datetime 0.8.0 setup](https://github.com/Kotlin/kotlinx-datetime/blob/v0.8.0/README.md#using-in-your-projects), [date components](https://github.com/Kotlin/kotlinx-datetime/blob/v0.8.0/core/common/src/format/DateTimeComponents.kt), [Kotlin 2.1.21 Instant](https://github.com/JetBrains/kotlin/blob/v2.1.21/libraries/stdlib/src/kotlin/time/Instant.kt), [KGP compatibility](https://kotlinlang.org/docs/gradle-configure-project.html#apply-the-plugin).

### Q4: Can a JVM-delegating root POM coexist with KMP metadata?

Agent: `verify_publishing` (general-purpose). Status: completed. Evidence: repository configuration and official Gradle/Kotlin documentation.

Yes, structurally. The helper rewrites POM XML but does not rewrite `.module` metadata or copy a JVM JAR into the root publication. Metadata-aware Gradle and POM-only consumers can follow different resolution paths. Correct metadata discovery and complete target publications must still be demonstrated.

The current snapshot workflow selects only root and JVM publications. Adding source targets without changing actual publication selection would be insufficient. Domain modules use `jvmMain.compileOnly(core)`; tests supply core explicitly, which can mask a missing downstream dependency.

Separate consumer checks should inspect staged metadata/artifacts, run a Maven JVM consumer, run KMP common/target consumers, and verify domain-to-core API exposure. If testing Gradle's POM-only path, disabling module metadata lookup must also disable metadata redirection; simply selecting `mavenPom()` is insufficient.

Apple final-binary linking and test execution require a Mac with appropriate Xcode/simulator tooling. Apple `.klib` cross-compilation defaults differ by Kotlin version: it was experimental/opt-in in the 2.1 generation and became stable/default in 2.2.20. Verify Intel simulator support against the chosen compiler rather than declaring that target removed based on historical plans.

Sources: [publishing helper](../../../../buildSrc/src/main/kotlin/utils/Publishing.kt), [provider conventions](../../../../buildSrc/src/main/kotlin/faker-provider-conventions.gradle.kts), [snapshot workflow](../../../../.github/workflows/master.yml), [Gradle module metadata](https://docs.gradle.org/current/userguide/publishing_gradle_module_metadata.html), [metadata source selection](https://docs.gradle.org/current/userguide/supported_metadata_formats.html), [KMP publication](https://kotlinlang.org/docs/multiplatform/multiplatform-publish-lib-setup.html), [Kotlin 2.1 cross-compilation](https://kotlinlang.org/docs/whatsnew21.html#ability-to-publish-kotlin-libraries-from-any-host), [Kotlin 2.2.20 cross-compilation](https://kotlinlang.org/docs/whatsnew2220.html#stable-cross-platform-compilation-for-kotlin-libraries), [Native targets](https://kotlinlang.org/docs/native-target-support.html).

## Reconciliation

| Initial claim | Result | Revision/action |
| --- | --- | --- |
| Current runtime and resource boundaries | Confirmed with important ownership/behavior details | Added current per-service caching, local/global uniqueness distinctions, stale KDoc and concrete migration pitfalls. Tasks 1, 5 and 7 own them. |
| Faker-version seeded stability needs more control | Confirmed | Clarified shared XorWow versus platform defaults, full sampling control and external-input conditions. SplitMix remains a candidate. |
| JSON/date APIs are feasible candidates, not a verified dependency tuple | Confirmed; compatibility caveats added | Added explicit date baseline/experimental instant/no offset-type facts and the current Gradle support-range caveat. No invented exact serialization minimum. |
| Root POM delegation is not inherently incompatible with KMP | Confirmed | Preserved the conditional decision; added real snapshot publication filters and metadata-discovery checks to Tasks 4, 21 and 22. |

No verified claim establishes successful compilation, publication, target execution, full pattern coverage or acceptable embedded-data costs. Those remain the explicit first milestone. No isolated agent reviewed the document's completeness or task sizing; run `$kk:review-design kotlin-multiplatform` separately.

## Final verified position

Proceed with the agreed scope and a bounded feasibility milestone. The repository has useful KMP build groundwork, but real common runtime behavior and complete publication are still work. Embedded full-locale data and portable generation are justified candidates; neither dependency examples nor source inspection substitute for cross-target and independent-consumer evidence. The reconciled design and implementation plan preserve those limits explicitly.

## Document validation

Authoring checks passed for local file links and heading anchors, fenced blocks, trailing whitespace, required task metadata, all 24 task identifiers and an acyclic dependency graph. All 97 implementation subtasks remain unchecked. These checks validate document structure, not implementation behavior or independent design quality.

Rendered-site validation remains unverified: captured `mkdocs build` exited 127 because `mkdocs` is not installed in this environment, and a Python module probe also found no MkDocs module. No documentation dependencies were installed or repository configuration changed to bypass that result. Next action: use the repository documentation environment with `requirements.txt` installed and run `mkdocs build`. This limitation is separate from the future KMP feasibility/build tasks, none of which were run during design.
