# KMP feasibility evidence

Execution evidence for completed Tasks 1–4 and 25. Work began on 2026-11-23 at
`9a3844298b58c45d60e01d39cc72694163fcbdd1`; each section records its own candidate
and verification. All five tasks completed after their hosted matrices and
independent reviews passed. The mechanism feasibility milestone is complete;
production migration remains pending. The inherited repository-check failure still limits
whole-PR readiness.

Detailed [sampling](#task-25-controlled-seeded-operations) and
[regex](#task-3-portable-regex-generation) specifications/evidence appear below;
execution status is tracked in tasks.md.

Task 4's independent publication checks and final reviews passed, as recorded
below. The completed hosted matrix supports proceeding to production setup in
Task 26; it does not establish production publication or release readiness.

## Trial configuration

The standalone [harness](../../../../test/fixtures/kmp-feasibility/) has its own
settings and is absent from production settings, publication aggregation and the
BOM. It contains dependency/API tests and a JVM build-time inventory task; it
contains no copied Faker facade, provider, resolver or uniqueness implementation.
No remote publication task is configured.

| Component | Trial selection / evidence |
| --- | --- |
| Producer Kotlin / KGP / kotlin.test | 2.3.21; JVM and Node API tests passed locally. |
| Gradle | Existing wrapper 8.14.3, inside KGP 2.3.21's supported range. |
| Compilation / Gradle JDK | Temurin 21; JVM bytecode target 11. |
| JVM runtime range | 11–21; both passed hosted verification. |
| JSON tree | kotlinx-serialization-json 1.11.0, no serialization compiler plugin. |
| Dates | kotlinx-datetime 0.8.0 and kotlin.time.Instant; no experimental opt-in. |
| Common tests | kotlin.test annotations/assertions; JUnit Jupiter 5.11.0 and platform launcher 1.11.0 on JVM; KGP runners on JS/Native. |
| Build-time inventory | Production versions SnakeYAML 2.2 and ICU4J 77.1. |
| Apple trial | macos-latest resolved to macOS 26.7.2, image macos-26-arm64 20261117.0516.1; explicitly selected Xcode 26.0.1 (17A400). Both compiler versions passed simulator execution and device linking. |
| Tested probe consumer compiler floors | JVM and KMP: Kotlin 2.3.20; Task 4's independent consumer passed on JVM 11/21, browser, Node and iOS simulator, including common metadata compilation and device linking. Actual production artifacts remain Tasks 29/21. |
| Published stdlib requirement | 2.3.21 in producer metadata and the resolved JVM/JS graph. The earlier source-only floor probe resolved 2.3.20; it is historical evidence, not the publication requirement. Native tests use the selected 2.3.20 compiler distribution; its logical Gradle stdlib dependency resolves to 2.3.21 but that Native Maven variant supplies no binary. No forced lower stdlib or metadata bypass. |
| Language/API settings | Compiler defaults, no metadata-version skip flags. No explicit lowering of producer metadata. |

Kotlin 2.4.21 is newer, but the selected stable 2.3 patch line is sufficient for
the candidate libraries and retains the existing Gradle wrapper. This deliberately
limits the migration's toolchain change. Serialization 1.11.0 is built with Kotlin
2.3.20; that alone does not establish a consumer compiler floor. The dependency
probe checks actual resolved APIs instead. Production convention plugins (BCV
0.17.0, Dokka 2.0.0, Spotless 7.2.1, NMCP 1.0.2 and semantic versioning) are not
applied to this isolated harness. Their existing pins remain unchanged and their
compatibility must be checked in Task 26 before production adoption.

An earlier Kotlin 2.2.21 trial failed: the JVM compiler required ExperimentalTime
opt-in for the timestamp path and the JS compiler could not resolve the selected
stdlib/serialization APIs. Capture `3b1ab7a7-51c4-4872-819e-d1f1b8c18d16`
retains the diagnostics. Kotlin 2.3.20 subsequently compiled and ran the same JVM
and Node cases without opt-ins or metadata bypasses (capture
`0eebd6b5-8c36-40fc-b5b1-f422cc14df3b`). Select 2.3.20 as the practical candidate
floor aligned with serialization's compiler baseline, not a claim that every
earlier patch/compiler combination has been exhaustively ruled out.

`dependencyInsight --dependency kotlin-stdlib --configuration jvmTestCompileClasspath`
at `-PprobeKotlinVersion=2.3.20` resolves stdlib **2.3.20**, choosing it over
datetime's 2.1.20 requirement. Capture `c0bd62dc-db67-42b8-91c8-df5636581407`
records that dependency graph. The compiler-floor probe did not silently select
the producer's 2.3.21 stdlib. Keep the compiler and runtime-library requirements
explicit when building the independent consumers in Tasks 4/29/21.

The optional Intel iOS simulator is not advertised by this trial: the selected
matrix exercises Arm64 only. Android remains a JVM consumer. There are no desktop
Native, Wasm, npm-export or Swift SDK additions.

Sources: [KGP/Gradle compatibility](https://kotlinlang.org/docs/gradle-configure-project.html),
[Kotlin release history](https://kotlinlang.org/releases.html),
[serialization 1.11.0](https://github.com/Kotlin/kotlinx.serialization/releases/tag/v1.11.0),
[datetime 0.8.0](https://github.com/Kotlin/kotlinx-datetime/tree/v0.8.0), and
[macOS runner software](https://github.com/actions/runner-images/blob/main/images/macos/macos-26-arm64-Readme.md).
Runner images change; actual run logs, not this software listing, establish the
validated environment.

## Dictionary inventory

Run `./gradlew -p test/fixtures/kmp-feasibility inventoryInputs`. Reports go under
`test/fixtures/kmp-feasibility/build/inventory/`; they are generated evidence, not
a second maintained dictionary source. Hosted runs retain them as artifacts.

- 326 YAML files, 59 logical locale groups, 6,451,142 UTF-8 source bytes.
- English category files occupy 1,736,958 bytes; the separate `en.yml` adds 136
  bytes (total 1,737,094). Japanese `address.yml` is 2,534,253 bytes.
- After SnakeYAML alias resolution: 321,937 string values, 2,234 integer values,
  one Boolean value, 3,531 lists and 1,977 maps. Keys are counted separately.
  No null, floating-point or timestamp values were encountered.
- 338 list-within-list occurrences, maximum traversed node depth 7, and 963
  strings containing expression openers. These counts describe source shapes,
  not a claim that arbitrary-depth resolver merging works.
- `uk.yml` has four anchors (`male_first_name`, `male_last_name`,
  `female_first_name`, `female_last_name`) reused in first/last-name lists.
  SnakeYAML expansion retains nested lists; flattening them would change sampling.
- `files.tsv` records each file, logical locale, parsed locale key and categories.
  `scalar-exceptions.txt` records every non-string scalar and non-string key,
  including nested paths. Preserve integer/string distinctions and array order.

### Existing data discrepancies requiring explicit disposition

1. Bare `no:` in `no.yml` is parsed as Boolean `false` by the existing YAML 1.1
   loader. Its logical filename locale remains `no`; the inventory records both.
   The production Jackson conversion can therefore emit a `"false"` root key.
2. `es-AR.yml` → `address.country_by_name.noruega` is Boolean `false`, also from
   unquoted `NO`. It is not an ordinary string value.

Neither input was corrected in Task 1. **Owner: Task 2 implementer.** Before
embedding, characterize these entries with the production conversion, choose and
document a scoped correction or preservation policy, and prove every source
value is accounted for. Silent coercion or dropped locales is not acceptable.

**Task 2 disposition:** Preserve both results exactly in this probe. The generator
uses the same SnakeYAML 2.2 → Jackson 2.21.1 conversion before partitioning; its
manifest records `no.yml`'s logical locale `no` and parsed root key `false`
separately. Full-document reconstruction and explicit shared assertions verify
the Boolean scalar and parsed root. No YAML was edited. **Owner: Task 5
implementer**, decide and document any resolver-facing correction before production
integration; do not mistake successful storage of these values for a data fix.

## Resolver and resource boundaries

The source baseline is `FakerService.kt` and its existing `FakerServiceTest.kt`:

| Required characterization | Existing evidence / next production check |
| --- | --- |
| English defaults plus partial localized keys | Spanish address, German commerce and Japanese cat tests. |
| Base then exact locale | `fr-CA` state overrides with French ancient-god fallback; `de-DE` and `fr-FR` base fallback. |
| Locale spelling | `fr_FR` dictionary access is normalized to `fr-FR`. Locale-aware random strings use a different Java Locale path; see below. |
| Secondary resource identity | Creature/game sibling resources accumulate under one primary category today; Task 5 must check reversed sibling loads and cold/warm views. |
| Unknown locale | `pe` throws a dictionary-not-found error. |
| Phone and cell phone | Separate branches; phone fills missing English fields, cell phone falls back as a whole when absent. |
| Currency symbol and separator | Scalar categories use locale-wide files and English fallback. |
| Nested keys | Resolver accepts one, two or three components. Source explicitly documents incomplete third-level localized merging; Task 5 owns characterization and any scoped fix. |
| Cross-category references | Dictionary expressions resolve data without requiring a matching domain-provider class. |

Direct legacy consumers: `FakerService` classloader JSON access; core
`integration/FakerIT.kt` classpath YAML locale enumeration; YAML-to-JSON Gradle
resource processing; CLI native-image `resource-config.json` broad `.*/*.json$`
include. Documentation includes and contribution instructions reference authoring
YAML, which stays in the repository. BLNS loads its own JSON and pictures loads
JPEG resources; those are separate assets and must survive Task 20's resource
configuration cleanup.

## Regex inputs after provider preprocessing

`regex-inputs.tsv` preserves source/key/input and a syntax representative after
preprocessing for every source occurrence: 122,923 rows (120,290 address, 2,529
vehicle, 68 bank, 36 finance). Large Japanese literal postcode lists explain most
rows. Random digits/letters are represented by `0`/`A`; this is a syntax inventory,
not enumeration of every random result or a new implementation of generation.

| Caller | Actual preprocessing before regex generation |
| --- | --- |
| `Address.postcode` | Inputs containing `#` use numerify only and never reach regexify. Other inputs reach regexify unchanged; `/` is removed afterwards. |
| `Bank.ibanDetails` | Lowercase country lookup, map-to-string extraction of its last `bban_pattern` entry. Inventory checks that ordering assumption for each entry. |
| `Finance.creditCard` | Replace `#` with digits, then regexify; slash delimiters and `L` remain input literals. |
| `Vehicle.licencePlateByState` | Replace `#` with digits and every `?` with an uppercase letter before regexify. This includes question marks that look like regex syntax. |
| `CryptographyProvider` | Computed `[a-f0-9]{length}`; supported functions choose 32, 40, 56, 64, 96, 128 characters. |
| `StringProvider.regexify` | Arbitrary caller input; Regex overload passes its pattern string, not options. Document the portable grammar in Task 3/8. |

The extracted corpus has 312 distinct nonliteral syntax representatives. Task 3
must classify all of them, including grouping/alternation, classes/ranges, escapes,
repetition and delimiters. Existing public regression examples in
`FakerServiceTest` include `^AKIA\S{16}$`, `^\S{40}$`,
`^i-0[\da-f]{16}$` and `^arn:.+:.+:.*:([0-9]{12}):(.+)$`; these add anchors,
non-whitespace classes and unbounded repetition to the compatibility inventory.
Unsupported public syntax may become an explicit major-release break, but no
bundled pattern can be silently excluded. This inventory does not implement or
approve a generation grammar.

## Locale character behavior and Java APIs

`locale-characters.tsv` records the four ICU exemplar sets for every dictionary
locale plus `eo`, `fil-PH`, `und`, `zz-ZZ`, and `en_US`. Existing random-string
code accepts arbitrary `java.util.Locale`, independently of dictionary presence;
Esperanto and Filipino demonstrate valid inputs outside the bundled dictionary
set. Unknown `zz-ZZ` currently receives ICU fallback data. `und` and raw
`Locale.forLanguageTag("en_US")` have empty standard/auxiliary/index sets; the
underscore form becomes `und`, unlike dictionary normalization. With positive
length and numerical/punctuation options disabled, sampling an empty set fails.

Existing code uses only ICU code-point ranges; it ignores exemplar string
elements (for example multi-character entries). No supplementary ranges appeared
in the sampled table, but string elements did. Task 12 must preserve or explicitly
revise this accepted-input/option behavior; it cannot silently narrow to dictionary
locales or substitute ASCII. Pin ICU data and iteration order when generating
tables. Host fallback behavior is evidence, not a portable policy decision.

Portability seams found in core: Jackson/InputStream, `EnumMap`/Java map helpers,
`Matcher`/`StringBuffer`, Java locale casing, RgxGen/`asJavaRandom`, ICU exemplar
sets, UUID, Java date/time types and wall-clock reads, `String.format`, Java enum
`Class`, reflective unique clones/property inspection, class-construction
configuration and parameter reflection. `Dictionary`, `Mapper`, `FakerService`,
`IRandom`, `RandomService`, `Person`, `Internet` and uniqueness/provider bases own
these paths. Pictures' AWT/ImageIO and class generation/Java helpers intentionally
remain JVM-specific; ordinary domain providers otherwise rely on the core seams.

## Coverage and verification boundaries

The baseline has no JaCoCo/Kover configuration or coverage threshold. Select
Kover JVM instrumentation for shared production source exercised by commonTest,
plus existing JVM tests; Task 31 must pin a compatible plugin, establish line and
branch reports for changed handwritten code, and enforce the repository's 100%
new-code requirement before Task 5. Generated payload text should be verified by
source-to-output content checks in Task 2, not inflated line counts.

[Kover collects JVM/Android coverage](https://github.com/Kotlin/kotlinx-kover).
It does not measure JS or iOS execution. Those targets still require the same
semantic tests and seeded vectors; do not label their coverage 100% from JVM
instrumentation. The Task 1 harness adds dependency tests/build tooling, not
production generation behavior. It does not install the later production gate.

| Environment | Required command in the standalone harness | Evidence |
| --- | --- | --- |
| JVM 21 | `jvmTest` | 3 tests, zero skipped/failures locally. |
| JVM 11 | `jvmTest -PprobeTestJdk=11` | 3 tests passed in hosted run 45205602246. |
| JS Node | `jsNodeTest` | 3 tests, zero skipped/failures locally. |
| JS browser | `jsBrowserTest` | 3 tests passed for producer 2.3.21 and compiler floor 2.3.20 in hosted run 45205602246. |
| iOS simulator Arm64 | `iosSimulatorArm64Test` | 3 tests passed with each compiler: floor 2.3.20 in job 137800354478, producer 2.3.21 in successor job 137808660696. |
| iOS device Arm64 | `linkDebugTestIosArm64` | Dependency test consumer linked with both compilers in those Apple jobs. Physical-device execution is outside scope. |

Local capture `2538e203-fae9-4c53-a0af-fc712caebb48` completed successfully with
`inventoryInputs jvmTest jsNodeTest`. XML reports independently confirm three
named tests per runtime. The initial inventory attempt
`3f65fbfb-e0ae-4fba-b837-38f5d5176e27` failed on the Norwegian key, after which
the inventory was corrected to report logical and parsed keys separately.

The [hosted run](https://github.com/serpro69/kotlin-faker/actions/runs/45205602246)
finished **successful** on attempt 2. All five jobs passed: `jvm-web`, `jvm-min`,
`compiler-floor`, `ios-compiler-floor` and `ios`. Together they execute 27 test
cases across nine runtime/compiler configurations, with no skipped/failed cases.
Both Apple jobs also compiled/linked their simulator and device test binaries.
Producer Apple successor job 137808660696 passed. Its initial job
137800354997 never obtained a runner, so after checking all other jobs had
completed, the queued run was cancelled and only that job rescheduled. No failed
test was retried or dismissed. Attempt 2 retains the successful earlier jobs.
The first run 45205187415 passed its JVM/web job before being superseded
and cancelled by the expanded matrix; its overall cancelled status is not a pass.

The dedicated Actions workflow checks named, non-skipped XML cases per requested
runtime, and retains reports even on failure. No missing/disabled target counts
as success. Both Apple XML reports were downloaded and inspected; the producer
task log is also retained in capture `90f37c85-5b01-48d4-a0fc-dcfcf714501e`.
Task 1 verification and final independent review are complete.
Fixture success does not validate production publication hooks, signed
uploads or API compatibility of the eventual Faker implementation.

## Independent review

Source review of base `9a3844298b58c45d60e01d39cc72694163fcbdd1` through
`61c720914` inspected all nine changed files and unchanged regex/resolver/locale
callers. The isolated code-reviewer reported no substantiated source defect;
its acceptance verdict was COMMENT while Apple evidence and the explicit stdlib
declaration were outstanding. The stdlib evidence above addresses the declaration.
The subsequent evidence review inspected the downloaded Apple-floor XML and
timestamped task log, confirming three passing tests and both link tasks.
The final evidence review also inspected the producer Apple XML/log and returned
**APPROVE**, with no remaining Task 1 findings or evidence requests. Non-Apple
execution and dependency-resolution results are author-attributed inputs to that
review; the reviewer did not run tests itself.

External review discovery selected PAL's Claude CLI (Anthropic, initially inferred
from the shipped client mapping; observed configured model `claude-opus-5-5`).
Its sole attempt failed before reading source because OAuth had expired and could
not refresh. No external findings or independent corroboration are claimed.
Other clients were unavailable; Gemini is retired. The internal reviewer remains
the only completed source review. There are no systemic findings to index.

## Task 2: complete embedded payload

Implementation started from `ae5373ab90129e0c8ddb5d5660b71ca4e931f7cf`.
The preferred chunked-JSON representation has passed the target matrix in the existing
standalone harness. No production data provider or resolver is copied here.

`payload.gradle.kts` expands YAML aliases using the production loader, performs
the production JSON conversion, orders object keys lexically and partitions
each source into its individual faker categories. Non-faker root metadata is
retained in a separate projection. Arrays and scalar kinds are unchanged.
The generated manifest retains source paths, logical locales, parsed root keys,
resource names, locale layers (`en`, base, exact) and source-kind ranks
(locale-wide, primary resource, secondary resource). The ranks classify files;
they are not a universal overlay algorithm. Default English selects a secondary
resource instead of combining it with a primary resource; localized layers use
the ordered source inputs. Task 5 implements those selection/merge rules. The
manifest records ordering inputs;
it does not perform fallback merging or expression resolution.

Each projection has its own supplier file; string chunks are bounded to 8,000
UTF-16 units and 24,000 modified-UTF-8 bytes, preserving surrogate pairs. Separate
literal-returning methods and append calls prevent giant folded constants.
Manifest factory groups contain at most 32 entries. Only Kotlin sources under
`build/generated/payload/commonMain/` are compiled; no YAML/JSON resource copies
or runtime network/asset setup are added.

Initial generation produced 666 projections from all 326 files/59 locales,
1,020 literal chunks, 4,265,767 UTF-8 JSON bytes and 5,104,345 generated Kotlin
source bytes. Observed maxima: 8,000 UTF-16 units and 13,941 modified-UTF-8
bytes. These are generation measurements, not published package sizes.
The local JVM/Node run passed (capture
`4764ee75-f998-41ac-bda1-bd4cfdc604ef`): 9 JVM cases, including complete original
document reconstruction, and 8 Node cases. Both enumerated all 666 projections.
The strengthened checks subsequently passed the full hosted matrix below. The
first build-script compile attempt
`5a41a0d8-b8b9-4908-bfda-d2326793a36c` failed on ambiguous Kotlin `sumOf`
overloads; an explicitly typed integer fold fixed the generator before execution.

Verification compares each compiled projection's UTF-16 length and 64-bit FNV-1a
fingerprint against constants calculated from source JSON before compilation.
This catches target-specific literal changes that a self-round-trip could miss.
Expected values are generated only into `commonTest`; they are absent from main
artifacts. The verifier uses [FNV-1a constants](https://www.ietf.org/ietf-ftp/rfc/rfc9923.pdf)
over UTF-16 code units, with independently calculated ASCII/Unicode/surrogate/
escaping/NUL vectors. It is an integrity check for accidental compiler/encoding
changes, not a cryptographic authenticity guarantee. Common tests then round-trip
every projection and assert canonical object ordering on every target; a JVM
test reads every original YAML document independently and compares the entire
converted JSON tree with one reconstructed from compiled projections. Dedicated
shared cases exercise a single German category without decoding sibling
categories, repeated access, distinct source identities, missing projection
failure, Ukrainian aliases, the two Boolean quirks, English time metadata and
the large Japanese address projection. Full corpus enumeration is a verification
pass; ordinary category access remains lazy.

### Migration follow-up: map order and IBAN extraction

Canonical ordering places `bban_pattern` before `length`, while current
`Bank.ibanDetails` extracts the last field from a stringified map. Values survive
unchanged, but blindly retaining that parsing would select the length instead
of the pattern. **Owner: Task 15 implementer, coordinated with Tasks 5/8.** Read
the named `bban_pattern` entry through the common resolver and test all IBAN
patterns; do not rely on map rendering order.

This also affects generic `FakerService.getRawValue` paths: the two-/three-key
overloads sample `values.toList()`, and nested maps can be rendered with
`toString()`. **Owner: Task 5 implementer, coordinated with Tasks 6/8.** Audit
both map sampling and rendered-map callers, define the deterministic key order
explicitly, and add semantic/seeded cases. Major-release seeded output may change,
but accidentally interpreting the wrong map field is not acceptable. Canonical
sorting stays as specified by the design. These provider/resolver adaptations
are deferred because Task 2 changes no production behavior.

### Hosted verification and measured costs

[Run 45214931092](https://github.com/serpro69/kotlin-faker/actions/runs/45214931092)
passed on implementation commit `b9942303bdd6447332bc90315f7815d2774f945a`.
All five jobs passed, with **75 cases and zero skips/failures/errors**: 9 JVM
cases on each of three JVM configurations, 8 cases on each of four JS
browser/Node configurations, and 8 cases on each of two iOS simulators.
Both Apple jobs linked simulator and physical-device test binaries. Physical
device installation/execution remains outside the agreed scope.

All five generated manifests agree on the main-source SHA-256
`5f9803a27c6b55f27ec011c88205d39c4859b5ceef4a99b3fb7ab506d23b7649`.
They also agree on 59,413 generated test-source bytes for the independent
fingerprint expectations. A separate local regeneration matched the main hash
(capture `b56940b4-7432-482c-8aa6-8e5e612e6741`). Capture
`99c23dc9-5c83-4de7-a294-52756b339859` checks the downloaded final XML/manifests
and totals all 75 passing cases. The earlier run 45212550760 passed before the
fingerprint improvement; it is retained as history, not substituted for the
strengthened checks.

| Output (producer Kotlin 2.3.21) | Bytes | Interpretation |
| --- | ---: | --- |
| JVM JAR | 2,613,474 | Probe main classes plus complete embedded payload; excludes test expectations. |
| JS main klib directory | 5,691,149 | Unpacked compiler output, not a download bundle. |
| JS payload module | 5,209,654 | Development JavaScript; test code and runtime dependencies are separate modules. |
| iOS device main klib directory | 5,672,741 | Main compilation output. |
| iOS simulator main klib directory | 5,672,751 | Main compilation output. |
| iOS device debug test binary | 17,444,296 | Includes tests, expectations and runtime libraries; not the eventual library distribution size. |
| iOS simulator debug test binary | 17,595,240 | Same test-binary limitation; dSYM files measured separately. |

The 2.3.20 floor has the same JVM/JS main sizes and iOS main klib sizes in this
run; its device/simulator test binaries are 17,445,296 / 17,596,280 bytes.
Artifact reports exclude incremental compiler caches, and include unpacked klib
directories as well as packaged outputs. No published-package or tree-shaken
download-size claim follows from these measurements.

| Job | Timed build wall time | Reported maximum RSS |
| --- | ---: | ---: |
| Producer JVM/browser/Node | 112.13 s | 120.18 MiB |
| JVM 11 | 57.37 s | 122.89 MiB |
| Compiler-floor JVM/browser/Node | 115.64 s | 123.09 MiB |
| Producer iOS | 91.00 s | 2,444.59 MiB |
| Compiler-floor iOS | 224.35 s | 2,357.61 MiB |

These are `/usr/bin/time` reports around the Gradle invocation, including setup
and tests. They are **not aggregate memory measurements**: detached compiler
daemons can fall outside the timed process accounting, particularly on JVM/JS.
They must not be used to claim the whole JVM/JS build needs only 120 MiB.
Separate Gradle profiles measured producer generation at 2.194 s, JVM compilation
at 31.950 s, JS compilation at 22.523 s, iOS simulator/device main compilation at
8.535 / 2.847 s, and their test linking at 13.526 / 9.933 s. Floor iOS generation
took 3.119 s, main compilations 17.664 / 11.314 s, and links 34.470 / 29.579 s.
These are observations with differing hosts/caches, not a comparative benchmark
or performance SLA. Existing runner labels/capacity and default memory settings
were sufficient; no larger machine or heap increase was requested.

The lazy-access case observes decoded counts **0 → 1 → 1 → 2 → 3** for cold
German names, repeated names, another German category and English names; a failed
lookup leaves the count at 3. The full-corpus test deliberately creates a fresh
store per projection. It proves complete availability without making ordinary
lookups eager. Provider initialization/cache/uniqueness costs remain production
work in Tasks 5–13.

### Review corrections and remaining notes

The internal reviewer found no source defects and requested final hosted evidence.
The external Claude reviewer found a P2 gap in the original cross-target fidelity
test: each target compared compiled JSON with its own round-trip. The generated
test-only fingerprints above address that gap; the strengthened checks require a
fresh target run, which passed above. Its P3 suggestions led to broader map-order follow-up, explicit
source-kind semantics/English selection documentation, and an updated build-script
comment. External read receipts were stripped by PAL's normal response, so its
findings are attributed without claiming independently verified read coverage.

The follow-up found the fingerprint correction sound and no remaining P0/P1/P2
source finding. Task 5/15 now link directly to the map-order work in both tasks
and implementation documents. The optional test-name nit is retained as author
context: the test still asserts locale-layer overlay order as well as source-kind
classification, so its existing name remains descriptive.

The reviewer also raised JS timeout headroom. The final Node full-corpus case
took 1.237 s (producer) and 0.965 s (floor), with no timeout failures; the browser
cases also passed. KGP 2.3.21's `KotlinMocha.kt` source confirms a 2 s default.
Current settings are retained rather than treating the suggested 1.3 s observation
threshold as a new acceptance SLA. **Owner: Task 3/4 harness implementer**, reassess
per-case runtime against configured limits as the regex/consumer corpus grows;
split expensive cases or set an explicit justified timeout if headroom becomes
insufficient, while retaining complete corpus coverage. Browser XML timings are
rounded near zero here and are not a useful performance benchmark.

Both independent reviewers subsequently returned **APPROVE for Task 2** after
the strengthened matrix and documentation were supplied. No P0/P1/P2 finding or
Task 2 acceptance prerequisite remains. The internal reviewer inspected all five
sets of XML/manifests and the profiles; the external reviewer inspected a subset
and used the supplied aggregate for some counts/costs. External read trace receipt
remains unverified because PAL strips it. Neither reviewer executed the tests.

### Separate inherited repository check failure

The repository-wide PR workflow [45214943353](https://github.com/serpro69/kotlin-faker/actions/runs/45214943353),
also on `b9942303b`, failed its macOS commerce integration check. The failure is
`CommerceFakerIT` → `cannabis#strains` → “resolved value should not contain yaml
expression”: the returned **`Lime #5`** matches the helper's blanket
`#\{.*}|#++` regex but is absent from `valuesWithHashKey`.

`core/src/jvmMain/resources/locales/en/cannabis.yml` already contains that literal;
`Cannabis.strains()` returns dictionary values without numerifying them. Both
files and `test/.../IntegrationTestHelper.kt` are byte-identical at the Task 2
base and candidate. The helper already exempts other legitimate literal-hash
values. This is an inherited test/data mismatch, not a failed payload integrity
check. Failure evidence is retained in capture
`5cc67d72-d252-46cf-8b39-4ee22f613efe`, original job 137834447800.

**Owner: Task 15 implementer / PR maintainer.** Add a narrowly justified literal
exception for `Lime #5` using the existing helper convention (or a more precise
unresolved-template check), add/perform deterministic coverage of that literal
and a genuinely unresolved placeholder, then rerun the focused commerce suite
and the failed macOS check. Do not alter the dictionary value or retry the random
suite merely to hide the failure. It is deferred under the design's instruction
to record unrelated failures rather than expand this mechanism-only task.
**Task 2 is verified; the full PR check gate remains failed until this is fixed.**
API, Linux/Windows checks, CodeQL, docs and native CLI checks passed on this source
candidate. No merge or release readiness is claimed.

Both reviewers inspected this qualification and retained Task 2 approval: the
unchanged legacy assertion leaves no payload-task criterion unmet. The recorded
fix and deterministic verification remain prerequisites for treating the full
PR gate as ready, regardless of whether a later random sample happens to pass.

## Task 25: controlled seeded operations

This probe separates `SplitMix64` (raw 64-bit words) from `RandomSampling`
(derived operations) under `test/fixtures/kmp-feasibility/src/commonMain/kotlin/probe/random/`.
Both are internal, have no payload/provider dependency, and use no platform RNG
or `kotlin.random.Random` algorithms. Production still uses its existing JVM
implementation. The specification below is the candidate for promotion in Task 6,
not a promise to preserve old JVM sequences or cross-release output.

### Raw stream and seed mapping

The implementation adapts [Sebastiano Vigna's public-domain SplitMix64 reference](https://prng.di.unimi.it/splitmix.c),
with attribution beside the code. A signed `Long` seed initializes the state with
the same 64-bit two's-complement representation. Zero, negative values and both
signed extremes are valid; there is no warmup, hashing or family-specific mixing.
For each word, first add `0x9e3779b97f4a7c15` to state, then mix a copy:

```text
z = (state xor (state >> 30)) * 0xbf58476d1ce4e5b9
z = (z     xor (z     >> 27)) * 0x94d049bb133111eb
word = z xor (z >> 31)
```

All additions and multiplications operate modulo `2^64`; all shifts are logical.
Kotlin unsigned conversions retain the signed seed's bits and `ULong.shr` fills
with zeros ([conversion contract](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/to-u-long.html),
[shift contract](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-u-long/shr.html)).
Each instance owns independent mutable state. Sequential access is assumed;
thread safety and cryptographic unpredictability are outside this contract.

### Sampling and consumption specification

Let `W` be the next unsigned 64-bit word and `M = 2^64`. Every scalar operation
consumes a fresh whole word, including singleton intervals and bound one. No
operation caches leftover bits. Invalid inputs throw `IllegalArgumentException`
before drawing any words.

| Operation | Result and consumption |
| --- | --- |
| `nextInt()` | Interpret the high 32 bits of one word as signed two's complement. |
| `nextLong()` | Interpret all bits of one word as signed two's complement. |
| `nextBoolean()` | True iff bit 63 is set; consume one word. |
| `nextInt(bound)`, `nextLong(bound)` | Require a positive bound; return `[0, bound)` using the rejection rule below. Both use all 64 random bits. |
| `intInRange(first,last)` | Require `first <= last`; compute inclusive width in `Long`, sample an offset, then add `first`. Supports all `2^32` signed Int values. |
| `longInRange(first,last)` | Require `first <= last`; compute inclusive width modulo `M`. Width zero denotes the complete signed Long interval: use `W` directly as the offset. Otherwise use rejection sampling. Add `first` modulo `M`, then interpret as signed. |
| `nextFloat()` | `(W >> 40) / 2^24`, one word, exact binary fraction in `[0,1)`. Maximum is `1 - 2^-24`, IEEE bits `0x3f7fffff`. |
| `nextDouble()` | `(W >> 11) / 2^53`, one word, exact binary fraction in `[0,1)`. Maximum is `1 - 2^-53`, IEEE bits `0x3fefffffffffffff`. |
| `nextBytes(size)` | Require nonnegative size. Consume `ceil(size/8)` words; emit each word's least significant byte first. Discard any unused high-byte suffix at the end of the call. Size zero consumes nothing. |
| `shuffled(list)` | Copy the list, then for indices `n-1` down to `1`, sample `j` in `[0,i+1)` and swap `i,j`, even when equal. Preserve duplicates and caller input. Empty/singleton lists consume nothing. |

For a positive width `n`, calculate `t = M mod n` (in fixed-width Kotlin,
`(0uL - n) % n`). Draw until `W >= t`, returning `W mod n`. The accepted
interval has length `M - t`, divisible by `n`, so every residue has the same
number of preimages. Each rejection advances the raw stream and is observable
in subsequent calls. There is no retry cap or biased fallback; this internal
mechanism uses SplitMix64, whose full-period stream reaches every word. The
scripted source in tests is solely an independent consumption oracle.

The explicit `*InRange` names make both endpoints inclusive in this probe.
Task 6 must adapt legacy overloads deliberately: current `nextInt(min,max)` is
inclusive and current `nextLong(min,max)` is exclusive at `max`. This task does
not silently change those production APIs. Task 10 owns higher-level collection
subsets/UUID formatting; Tasks 6/13 own Faker configuration and family ownership.

### Independent vectors and semantic checks

`random_reference.py` is an offline Python 3 standard-library oracle. It uses
arbitrary-precision integers, computes state from `seed + position * increment`,
uses `divmod(2^64,width)` for rejection, and packs floating values as IEEE bytes.
It neither imports nor executes Kotlin. Run it from the repository root:

```sh
python3 test/fixtures/kmp-feasibility/random_reference.py
```

The six seeds are `0`, `1`, `-1`, `Long.MIN_VALUE`, `Long.MAX_VALUE`, and
`0x0123456789abcdef`. Each has five raw-word expectations and a mixed sequence:
raw Int, raw Long, bound 10, bound `2^62+1`, full Int interval, full Long
interval, `-17..19`, `-4000000000..5000000000`, Boolean, Float bits, Double
bits, eleven bytes, shuffle of `0..7`, then one raw Long. The final word checks
consumption after byte truncation and shuffle rejections. Expected constants are
checked in `RandomSamplingTest`, never regenerated during the Gradle build.

Eleven common tests additionally verify rejection at and below exact thresholds
(including maximum Int/Long bounds and width `2^64-1`), signed-range endpoints,
power-of-two and singleton consumption, Boolean/float endpoints, little-endian
byte blocks, discarded suffixes, shuffle order/input preservation/duplicates,
invalid inputs with no draws, independent instance state and range/permutation
invariants across 128 mixed iterations for each seed. This combines reference
equality with semantic assertions; raw RNG agreement alone is insufficient.

The initial local run passed all JVM cases but exposed a test-literal issue on
JS: the compiler emits the expected Float literal as decimal `0.99999994` while
the algorithm's exact binary fraction is `0.999999940395355224609375`. The endpoint
test now compares both IEEE bits and the exact Double value. It retains strict
equality, without a tolerance or changes to the generator. Kotlin documents JS's
wider Float range in [the bit-conversion API](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/to-bits.html).

### Verification and coverage

Run the existing harness matrix plus `randomCoverageVerification` on JVM:

```sh
./gradlew -p test/fixtures/kmp-feasibility jvmTest jsNodeTest jsBrowserTest randomCoverageVerification
./gradlew -p test/fixtures/kmp-feasibility jvmTest -PprobeTestJdk=11
./gradlew -p test/fixtures/kmp-feasibility iosSimulatorArm64Test linkDebugTestIosArm64
```

Repeat the existing compiler-floor jobs with `-PprobeKotlinVersion=2.3.20`.
The workflow requires all eleven named random tests to execute without failure
or skip in each requested runtime, alongside the existing payload/toolchain tests.
Device evidence is compilation/linking of the test consumer, not device execution.

The standalone build uses Gradle's built-in JaCoCo plugin with stable JaCoCo
0.8.16 ([Gradle 8.14.3 configuration](https://docs.gradle.org/8.14.3/userguide/jacoco_plugin.html),
[JaCoCo releases](https://www.jacoco.org/jacoco/trunk/doc/changes.html)).
`randomCoverageVerification` requires 100% JVM line and branch coverage of
`probe/random/**`; `randomCoverageReport` writes XML/HTML under `build/reports/jacoco/`.
An explicit prerequisite rejects missing execution data or an empty class tree
before JaCoCo can skip its report, and a covered-class minimum of two prevents
an empty measurement from satisfying the percentage limits.
This measures the two handwritten mechanism classes, excluding generated payload,
test code and the offline Python oracle. It does not measure JS/Native coverage
or change Task 31's selected production Kover gate. Target behavior still requires
actual common-test execution in each environment.

Local capture `84e29e1f-51fe-4dd3-96d1-14a7db7b5196` passed `jvmTest`, `jsNodeTest`
and `randomCoverageVerification`: 20 JVM cases and 19 Node cases, no failures,
errors or skips. The JaCoCo XML reports 46/46 lines, 32/32 branches and 359/359
instructions covered across the two executable classes. The raw-word interface
has no executable body. The initial failure is retained as capture
`5f879df3-d86c-47ae-8809-a7ffcf68e483`; the offline oracle output is capture
`8552d701-b941-4495-8455-3b30929f2449`.

Local browser capture `32c47e2f-f7bd-4281-81d6-2ee4a6019088` passed all 19 cases,
without failures/errors/skips, using installed Brave as the Chromium runner.
Root `spotlessCheck` passed in capture `3aecce10-0063-44e7-a770-423394ef2945`;
its configured source scope does not include this standalone fixture.

The local compiler-floor run (`-PprobeKotlinVersion=2.3.20`) also passed 20 JVM,
19 Node and 19 browser cases, with no failures/errors/skips, in capture
`01f06359-abdf-4b55-8228-d9a452b1607e`. These are producer recompilations at the
floor, not Task 4's later external-publication consumer checks.

The internal isolated reviewer found no source defects. External Claude review
(observed model `claude-opus-5-5`) found no sampling defect and suggested two P3
improvements: reject empty/missing coverage measurements and correct a comment
describing `2^62` as the signed boundary. Both are addressed and both reviewers
accepted the source corrections. The external reviewer checked the recovered
oracle output against all fixed vectors; only hosted evidence remained open.
PAL returned no observable file-read trace (`raw_events_removed_for_normal`),
so this external review is attributed without claiming verified source receipt
or independent corroboration. No systemic P0/P1 findings require indexing.

The corrected coverage check passes normally (capture
`8d5bd698-6f24-4f97-bc37-8b4760717aaf`). Controlled missing-input checks removed
the build's `.exec` file, then the compiled random class directory, with
`jvmTest` explicitly excluded. Both attempts failed at `requireRandomCoverageData`
with the corresponding missing-input diagnostic; original outputs were restored
(capture `b67543d9-cd12-4cb9-907f-62ee7855549e`). These are intentional negative
checks, not failed behavioral tests or retries.

### Hosted Task 25 evidence

[Run 45223240006](https://github.com/serpro69/kotlin-faker/actions/runs/45223240006)
passed all five jobs at `15f72144f61898aeb680161b40167035550e4800`:

| Job | Executed cases | Additional evidence |
| --- | ---: | --- |
| `jvm-web` | JVM 20, Node 19, browser 19 | Coverage verification passed: 46/46 lines, 32/32 branches, 359/359 instructions, two executable classes. |
| `jvm-min` | JVM 11: 20 | All eleven random tests executed. |
| `compiler-floor` | JVM 20, Node 19, browser 19 | Kotlin 2.3.20. |
| `ios` | Simulator: 19 | Kotlin 2.3.21; device test consumer linked in job 137863028760. |
| `ios-compiler-floor` | Simulator: 19 | Kotlin 2.3.20; device test consumer linked in job 137863027456. |

The downloaded XML totals **174 cases**, including all eleven random tests in
each of nine runtime/compiler configurations (**99 random-test executions**),
with zero failures/errors/skips. Both Apple job logs record the candidate SHA,
the simulator task and successful `linkDebugTestIosArm64`; physical-device
execution remains outside the agreed matrix. Existing payload/toolchain cases
also passed. Test reports and coverage XML are retained for GitHub's configured
artifact retention period; the counts and revision above are the durable record.

Earlier run 45222552566 was initially absent from GitHub's listing, so a manual
dispatch created 45222731919 and concurrency cancelled the original. The review
correction push then superseded 45222731919. Neither cancelled run is counted as
passing; no behavioral failure was retried. The final run above passed without
reruns, including the initially queued lower-compiler Apple job.

The user explicitly authorized pushing for iOS CI, superseding an earlier
local-only instruction. Final internal review approved Task 25 after inspecting
all hosted XML and both Apple logs. External review also accepted the task's
evidence and closed its requests. Its native file-read receipt remains unverified;
denied shell attempts do not establish inspection. The non-Apple compiler/JDK
attribution uses artifact names, reviewed matrix configuration and successful
jobs; Apple versions and device links are also explicit in the supplied logs.
No job-duration claim relies on metadata timestamps. No outstanding Task 25
finding remains. Production migration, external artifact consumption, full PR
readiness and the inherited commerce assertion are outside this approval.
No merge, production publication or release is authorized by this probe.

## Task 3: portable regex generation

Task 3's candidate grammar is fixed before implementation. It consumes the Task 1
post-preprocessing patterns and the proven Task 25 stream. These are internal
mechanism APIs; production `regexify`, preprocessing and Regex-overload options
remain assigned to Task 8.

### Grammar and random consumption

- Literals (including Unicode scalar values and `/`), concatenation, `(...)`,
  `(?:...)`, and `|` alternatives. Empty patterns/groups/alternatives are allowed.
- Character classes with ordered literals and ascending BMP ranges; a leading
  `^` complements the class within printable ASCII (`U+0020..U+007E`). Nested
  classes/intersections and supplementary code points inside classes are rejected.
  Each class is limited to 256 distinct characters to bound expanded alphabets.
- Escaped metacharacters and `\d`, `\D`, `\w`, `\W`, `\s`, `\S`, plus
  `\t`, `\n`, `\r`, `\f`, `\v`. Escaped hyphens are accepted only inside classes.
  Digit/word classes use ASCII; word order is
  digits, uppercase letters, lowercase letters, underscore. Whitespace order is
  tab, newline, vertical tab, form feed, carriage return, space. Complement shorthands
  and dot select from printable ASCII, retaining only matching characters.
- Optional root-leading `^` and root-trailing `$` assertions. Other anchor
  positions, lookarounds, backreferences, flags, named/atomic groups, Unicode
  property/hex/octal escapes and unknown escapes are rejected explicitly.
- Repetition `?`, `{n}`, `{n,m}`, `*` and `+`. The unbounded forms use a fixed
  maximum of **16** repetitions. Open brace bounds (`{n,}`), lazy and possessive
  suffixes are unsupported. Counts are nonnegative decimal integers with
  `n <= m`; an empty generation alphabet is rejected.

Literal/group/concatenation/anchor nodes consume no random words themselves.
An alternative with multiple branches consumes one bounded index; each character
class consumes one index, even for a singleton. Variable repetition consumes one
inclusive count before generating its body left-to-right; fixed repetition draws
no count. Class duplicates are removed while preserving source order. Generation
is not claimed to be uniform over all matching strings.

Parsing and validation finish before any random draw, including invalid syntax
in an alternative that might not be selected. Diagnostics report the UTF-16
start offset of the offending token, range, bound, sequence or repetition operator and a
reason. Unclosed delimiters refer to their opener; a missing count refers to its
expected start; an oversized whole pattern refers to position zero. AST nodes
retain their start so expansion-work errors identify the offending subtree.
The candidate limits patterns to 16,384 UTF-16
units, nested groups to 64, maximum generated output to 4,096 UTF-16 units and
generation work to 65,536 AST node visits. Limits apply to every parsed subtree,
including subtrees inside zero-count repetitions. The work limit also bounds
nested repetitions of empty groups, whose output-length bound alone is zero.
Oversized counts/expansions fail before random consumption. These explicit
limits bound parser recursion and output, and do not exclude any inventoried
bundled pattern. Supplementary literal pairs are treated as a single atom.

### Corpus and independent checks

`RegexCorpusTest` reads the already verified embedded projections and retains
source/key/input/transformed-pattern tuples. It applies only Task 1's documented
syntax preprocessing: skip address values containing `#`; replace finance `#`
with `0`; replace vehicle `#`/`?` with `0`/`A`; take the named bank BBAN field.
The generator itself has no payload/provider dependency. The JVM comparison
checks every tuple against the independently produced Task 1 YAML inventory,
without dropping duplicates or relying only on counts or fingerprints.

All **122,923 occurrences** are accounted for: **122,872 distinct patterns**,
including **312 syntax patterns** and **122,560 literals**. Every distinct literal
is parsed/generated and compared exactly; each syntax pattern is generated for
seeds `0`, `1`, `-1` and checked against a separately compiled Kotlin Regex.
The 936 syntax outputs also have a fixed length-prefixed UTF-16 FNV-1a fingerprint,
`61af7582c14d505d`, recorded from the JVM semantic run and matched on Node.
This fingerprint checks cross-target equality; it is not an independent semantic
oracle. Separate tests cover all six cryptography lengths and the four existing
FakerService regression patterns. Slash delimiters and finance `L` remain literals.

The reviewer-requested construct audit of the **312 distinct syntax patterns**
is nonexclusive (one pattern can appear in several rows):

| Construct | Distinct patterns |
| --- | ---: |
| Character classes | 290 |
| Ascending ranges within classes | 282 |
| Capturing groups / alternatives | 2 / 2 |
| Optional `?` | 4 |
| Fixed `{n}` / bounded `{n,m}` repetition | 51 / 60 |
| `\d` | 37 |
| Literal slashes | 17 |
| Negated classes, noncapturing groups, anchors, dot, `*`, `+` | 0 each |
| `\D`, `\w`, `\W`, `\s`, `\S`, other escapes, open brace bounds | 0 each |

The four inherited public regression patterns additionally require anchors,
dot, `\S`, `*` and `+`. Noncapturing/negative classes and the remaining ASCII
shorthands are part of the documented public-input subset, covered by focused
semantic and deterministic tests; they are not claimed as dictionary requirements.
Audit capture `f34ec972-1564-4b51-8843-a1a38d64f87b` used the hosted inventory
with SHA-256 `8e8e9d1eb3a39310f6b052fcd55bea7a50275db28e2518957f521c8671458c2d`.

Independent fixed generation vectors are reproducible with:

```sh
python3 test/fixtures/kmp-feasibility/regex_reference.py
```

That script expands five named patterns' choices directly using the Task 25
arbitrary-precision oracle, without importing any regex parser or Kotlin code.
It covers alternation, classes, fixed/variable/optional/unbounded repetitions,
negation, `\S`, dot, Unicode literals and the subsequent raw word for six seeds.
The expectations are fixed in `PortableRegexTest`, not regenerated by Gradle.
The initial standalone derivation is capture `c2234f9e-138a-42cd-b362-0b851d7748d0`;
the committed script's matching output is capture `ab8d0a1e-0641-4852-857e-9b7d8d48f710`.
AKIA-prefixed examples are artificial test strings, not credentials.

The matching oracle wraps each complete pattern in `^(?:pattern)$` before calling
`matches`. Initial capture `49fec876-d7e4-4eaf-82a3-9f07be699b8b` found that the
compiled Kotlin 2.3.21 JS matcher checks the extent of the first unanchored native
match: `(|a)()` chose the empty alternative and rejected `a` despite its valid
full match. Explicit anchors make the engine backtrack to the complete match.
The generator and the empty-alternative requirement were preserved.

### Verification and coverage

Use the existing five-job matrix, with the new named common regex cases required
in each runtime and the complete inventory comparison required in each JVM job.
`regexCoverageVerification` measures the handwritten parser/AST/generator package,
requiring 100% JVM line/branch coverage, at least nine covered classes, and
nonempty execution data/class files. `randomCoverageVerification` remains separate.
These measurements exclude fixture/test code and do not measure JS or Native.

Initial measured coverage exposed JaCoCo's exception-flow limitation around a
helper returning `Nothing`: caught exceptions did not mark some helper call sites.
Error branches now explicitly throw constructed diagnostics. The implementation
also removes an unreachable closing-bracket check and computes alternative
maximum length directly rather than inlining a collection operation's unreachable
empty-input path. No test was removed or threshold relaxed. Captures
`3c8f661c-8e0f-4f84-9d99-d903410549d9` and `d0b7e554-ee13-4f7f-ab61-cdd0c2a174b8`
retain those intermediate coverage failures; behavioral checks passed.

Local capture `70fc55c9-6b10-483c-a32e-951010f9139d` passed 30 JVM, 28 Node and
28 browser cases and both coverage gates. The regex report contains 165/165
lines, 197/197 branches, 1,444/1,444 instructions and ten covered classes; random
coverage remains unchanged at 100%. These are JVM measurements alongside
separate JS behavioral execution. No root build configuration changed; Task 25's
root Spotless result applies to its unchanged configured source scope, which
excludes this standalone fixture.

Controlled missing-input checks also passed: with `jvmTest` excluded and the
`.exec` file or compiled regex directory temporarily moved, verification failed
at `requireRegexCoverageData` with the corresponding diagnostic. Original outputs
were restored (capture `f66b6ac9-ab22-4fb3-a911-5289e33841d5`).

### Hosted Task 3 evidence

[Run 45228848073](https://github.com/serpro69/kotlin-faker/actions/runs/45228848073)
passed all five jobs on the first hosted attempt at
`95f89b358e930d1f918f01cc876eac0ffc773389`:

| Job | Executed cases | Additional evidence |
| --- | ---: | --- |
| `jvm-web` | JVM 30, Node 28, browser 28 | Both coverage gates passed. |
| `jvm-min` | JVM 11: 30 | Includes all nine common regex cases and the full inventory comparison. |
| `compiler-floor` | JVM 30, Node 28, browser 28 | Kotlin 2.3.20. |
| `ios` | Simulator: 28 | Kotlin 2.3.21; device test consumer linked in job 137882598531. |
| `ios-compiler-floor` | Simulator: 28 | Kotlin 2.3.20; device test consumer linked in job 137882598366. |

Downloaded XML confirms **258 executed cases**, including all nine common regex
tests in each of nine runtime/compiler configurations (**81 regex-test executions**)
and the additional inventory comparison in each JVM job. There are no failures,
errors or skips. This includes matching the full corpus fingerprint, independent
reference vectors and semantic/boundary assertions on both iOS compilers.
Both Apple logs identify the candidate SHA, simulator task and successful device
link. Physical-device execution is outside the agreed matrix.

Hosted coverage matches the local measurement: regex 165/165 lines, 197/197
branches, 1,444/1,444 instructions and ten classes; random 46/46 lines, 32/32
branches and two classes. Reports are retained for the configured GitHub artifact
retention period; these counts and the revision are the durable record. No job
duration claim relies on run metadata timestamps.

### Task 3 review corrections

The internal reviewer approved the initial implementation and its hosted evidence.
External review found no P0–P2 defect and suggested three P3 improvements plus two
nits, all addressed: AST/source offsets now identify diagnostic starts; limit
tests assert reasons and isolate the pattern-length rule with zero-output repeats;
all literal corpus generation uses a source that fails on any draw; scripted
first/last choices lock every shorthand alphabet's ordering; and inventory
locations are checked for uniqueness and exact key-set equality. Shared JVM
coverage data also has a neutral variable name. Generated values and consumption
remain unchanged.

Correction capture `f5055be2-26cf-4712-9686-4cc26ac19358` passed the same 30 JVM,
28 Node and 28 browser cases plus both coverage gates. Regex coverage is now
168/168 lines, 197/197 branches, 1,465/1,465 instructions and ten classes. The
earlier hosted measurement above is attributed to its original revision.
All 19 initial candidate/related snapshot hashes were checked against the review
provenance manifest; hosted `summary.txt` confirms `regexInputRows=122923`.

**Migration follow-up — owner: Task 8 implementer / Task 31 CI owner.** The
standalone workflow's inherited path filter excludes dictionary edits, and the
probe's counts/fingerprint intentionally record the inventoried snapshot. When
promoting the corpus guard, derive expectations from declared production inputs
and ensure dictionary edits trigger the target checks. Verify with a dictionary
change and an unsupported pattern that cannot pass by skipping its target job.
This does not weaken the current complete-corpus comparison or claim production
CI enforcement is already installed.

**Optional test hardening — owner: Task 8 implementer.** The shorthand tests pin
each alphabet's endpoints; the code and documentation specify the complete order,
but interior permutations of the non-corpus `\w` / `\s` alphabets are not directly
pinned. Before production promotion, add scripted indexes `0..n-1` and compare
the full documented alphabets. This was a nonblocking external-review suggestion;
no output defect or current acceptance gap was identified, and the ordering
contract has not been relaxed.

Final correction [run 45231592739](https://github.com/serpro69/kotlin-faker/actions/runs/45231592739)
passed all five jobs at `d53cfaae56ffd98a265265b7950049e6ac6d2ce6`, again with
258 non-skipped cases and all 81 common regex-test executions. The strengthened
diagnostic/limit/order/literal/inventory checks ran on every applicable target.
Both device links passed (producer job 137892268568, compiler-floor job
137892269183). Downloaded XML, both Apple logs and hosted coverage confirm the
same final 168/168 regex lines, 197/197 branches and 1,465/1,465 instructions;
random coverage remains unchanged. No failing hosted test was retried.

Final internal review approved the corrected source and all target evidence.
External review also approved Task 3, closed all original findings/evidence
requests, and left only the optional alphabet test hardening above; its wording
nit was clarified to “repetition operator.” No blocking finding remains, and no
systemic P0/P1 finding requires indexing.

The isolated workflow used the internal code-reviewer plus PAL `clink` with
Claude (observed model `claude-opus-5-5`). No API review provider was configured;
Claude was the eligible external CLI, other clients were unavailable/retired and
the authoring OpenAI provider was excluded. External native file-read receipt
was not exposed (`raw_events_removed_for_normal`), and some shell inspections
were denied. Its findings/approval are attributed without claiming independently
verified complete receipt or corroboration. It reported checking five snapshot
hashes; the full provenance verification is author-attributed. Internal review
inspected the supplied source, XML and logs without claiming to execute tests.

This closes Task 3's scoped acceptance. No bundled construct has been dropped or
silently approximated. Production integration, publication, whole-PR readiness
and the separately tracked commerce assertion remain outside this approval.


## Task 4: independent artifact consumption

Baseline: `ee1434410eb9e482c5a569e0358eb248a205b964`, 2026-11-23. The
standalone producer publishes a single mechanism module, with KMP root/target
publications, to `test/fixtures/kmp-feasibility/build/probe-repository/` under
`io.github.serpro69.kfaker.probe:kfaker-kmp-feasibility:0.0.0-local`.
Gradle's built-in `maven-publish` and KGP defaults supply the publications. No
production publishing helper, root-POM rewrite, signing or BOM is copied here.

The separate `consumer/` settings pins Kotlin 2.3.20; the producer remains on
2.3.21. Its `commonMain` entry point imports the published `MechanismProbe` class.
Both common metadata compilation and target execution are required. The consumer
has no included build, project substitution, producer source directory or Maven
Local repository; its Maven Central filter excludes the probe group. Its only
producer access is the file repository. The probe API exposes primitive values
and strings, delegating to the already-tested mechanisms without copying Faker,
provider, resolver or uniqueness code.

Five consumer cases cover every one of the 666 projections (326 sources, 59
locales), JSON decoding/round-trip equality, source/category identities, lazy
counts 0 → 1 → 1 → 2 → 2 after failure → 3, Japanese Unicode, seeded sampling,
regex vectors/independent matching, and rejection without random advancement.
The payload aggregate `14376e443dcf410f` was independently calculated in Python
from Task 2's generated pre-compilation expectations, in numeric part order:
FNV-1a over each `path\tcategory-or--\tUTF16-length\t16-digit-checksum\n` row.
The consumer recomputes individual checksums from the published strings before
folding those same rows. The fixed expectation is test-only; no producer test
sources or expected checksums are dependencies of the consumer. Task 2's existing
JVM source reconstruction remains the independent source-fidelity oracle.

### Compiler and runtime requirements

The source-only trial floor of Kotlin/stdlib 2.3.20 is distinct from the published
consumer result. The probe's generated metadata requires **stdlib 2.3.21** on
JVM/JS, while the independent consumer compiler stays **2.3.20**. No force,
metadata bypass or compiler upgrade is used. Native uses its compiler-bundled
stdlib; its Gradle report selects the logical stdlib 2.3.21 Native variant, which
has no binary artifact. The retained task log identifies the 2.3.20 Native
distribution. This is a declaration of the tested publication graph, not a claim that
stdlib 2.3.20 is incompatible or that every earlier compiler was ruled out.
Retain the producer's normal stdlib dependency and declare **2.3.21** as the
published JVM/JS floor instead of forcing an untested lower version. The intended
2.3.20 consumer compiler remains supported by the actual artifact checks.
Production consumer floors remain subject to actual artifact checks in Tasks
29/21. The other trial versions and target/JDK/Xcode requirements above are retained.

### Verification and pending decision

Local staging capture `f913fec8-472e-4be5-9393-c113ae2faaa0` produced root/JVM/JS
artifacts. Initial consumer capture `daa3c056-0564-434c-8f8c-87234451e16d` passed
five JVM and five Node cases. Capture `9310cf13-1217-47a2-88ee-c5c224e1868d`
failed `transformCommonMainDependenciesMetadata`: Linux had staged only
root/JVM/JS artifacts, but KGP resolves metadata for every declared target,
including iOS. The explicit common metadata compile now runs on macOS against
all staged variants. Linux still compiles the actual consumer common source into
JVM/JS artifacts; no advertised target or payload was removed to avoid the failure.

Final local capture `0497e43f-519a-487a-992b-10bac0201da3` passed **15 cases**
(five each on JVM 21, Node and Chromium) plus the published-boundary coverage gate.
Coverage XML reports 2/2 classes, 17/17 lines, 88/88 instructions and no branch
instructions in these wrappers. This is JVM measurement only. The Node complete
payload case took 1.025 s against the existing 2 s per-case timeout; no timeout
increase was needed. Browser XML rounds durations and is not a benchmark.
`dependencyInsight` capture `b0e4e095-775b-4f37-be53-ebc5da28baca` confirms JVM
stdlib 2.3.21 selected through the producer, with compiler/test libraries still
2.3.20. The workflow YAML and both inline evidence-check scripts parsed successfully.
Production Spotless conventions do not include this standalone fixture.

Hosted run [45241697525](https://github.com/serpro69/kotlin-faker/actions/runs/45241697525)
at `9b7ba49b42e5f98f2a6ef4245761e4fea4fecd2e` has passed all three consumer jobs:
**25 non-skipped cases** across JVM 11, JVM 21, Node, Chrome and iOS simulator.
The Apple consumer log proves execution of `compileCommonMainKotlinMetadata`
and `linkDebugTestIosArm64`, with 11 tasks executed and no up-to-date substitute.
All five original producer jobs also passed. The final run has **eight successful
jobs and 283 non-skipped cases**: 258 producer cases plus 25 external consumer
cases. Capture `0b1b6c70-ff90-4d66-90f0-250d07ac6413` checks every downloaded XML.
Both producer Apple configurations linked device binaries too. No failed test
was retried; queued Apple jobs eventually obtained runners in the original run.
The Apple job log records macos-26-arm64 image `20261117.0516.1`, Xcode 26.0.1
(17A400) and the consumer's Kotlin/Native 2.3.20 distribution. JDK 11 CI installed
Temurin 11.0.33+10 and ran the explicitly selected `probeTestJdk=11` launcher;
compilation used Temurin 21.0.13+9. Both independent reviewers approved the final
source/evidence and documentation corrections; Task 4 is complete.

The new facade and common consumer entry point are measured from the resolved
JVM JAR and consumer classes by JaCoCo 0.8.16. The gate requires both classes and
100% line/branch coverage; it cannot pass from an empty class set or missing
execution data. Existing random/regex coverage gates remain unchanged. Runtime
library downloads during an uncached Gradle build are ordinary dependency setup;
the published mechanisms make no dictionary network requests or asset-copy calls.

### Published sizes and costs

The complete Mac staging repository has five `.module` and five `.pom` files;
all root `available-at` links resolve to staged target metadata. These are standard
probe publications, not evidence for the production POM rewrite or Maven-only
fallback. Linux jobs deliberately stage only the variants they execute, so their
all-configuration dependency reports contain `FAILED` entries for unstaged targets.
Those entries are not claimed as passing resolution; the Mac repository and
explicit common compile cover the complete graph.

| Staged binary, Mac producer 2.3.21 | Bytes |
| --- | ---: |
| Root common metadata JAR | 241,474 |
| JVM JAR | 2,638,097 |
| JS klib | 1,820,961 |
| iOS device klib | 1,808,926 |
| iOS simulator klib | 1,808,990 |

Each Native target also has a 261-byte metadata JAR. Sources and POM/module files
are additional outputs, individually accounted for by `publication-files.tsv`.
The Linux JS klib was 1,820,963 bytes; these are observed packaged sizes, not a
claim of byte-identical archives across hosts, compressed application download
sizes, or the unpacked compiler-output sizes reported by Task 2.

| Profile | Producer staging | Consumer compilation/tests |
| --- | ---: | ---: |
| JVM 21 / browser / Node | 71.73 s | 92.18 s |
| JVM 11 | 68.55 s | 42.648 s |
| Mac / common metadata / iOS | 259.07 s | 183.59 s |

Mac producer generation took 4.271 s; full common metadata compilation 27.247 s;
device/simulator main compilation 20.686 / 23.777 s. The consumer common compile
took 7.830 s and its device/simulator links 19.865 / 28.405 s. These Gradle profiles
include different cache/download conditions and are not controlled benchmarks.
Task 2 retains the process-RSS measurements and their accounting limits; Task 4
adds no aggregate-memory claim. No runner capacity or heap setting was increased.
The iOS full-payload consumer case took 1.337 s; browser timings remain rounded.

### Review follow-up and decision gate

Initial internal and external reviews found no P0–P2 source defect and withheld
acceptance pending hosted evidence. External P3 comments prompted reconciliation
of the summary stdlib floor/status and a reproducible Python command in the
harness README. Capture `12370f86-1e4d-49a4-b7f8-0a5e38292a6f` executed that exact
documented command and reproduced `666 projections: 14376e443dcf410f`. The README
also identifies the seed-minus-one oracle and starts local publication from
`clean` to prevent mixing artifacts from earlier producer builds.

**Inherited follow-up — owner: Task 31 implementer.** The existing feasibility
workflow path filter watches the fixture/workflow but not dictionary-only changes.
The current CI evidence uses the exact reviewed corpus. When installing production
target gates, include authoring dictionaries in their triggers and verify a
dictionary-only change runs generation/consumer fidelity checks. This is not a
missing mechanism or a reason to change dictionary values in Task 4.

**Mechanism decision: feasible.** Retain bounded embedded JSON suppliers, lazy
per-category decoding, SplitMix64 with the recorded sampling contract, and the
proven portable regex grammar. Complete source fidelity, independent publication
consumption and the target execution matrix show no remaining mechanism blocker.
All locales remain included, synchronous and offline; no compiler/constant limit,
eager global decoding, seeded mismatch or additional CI capacity was required.
Both independent reviews accepted this scoped decision; Task 4 is complete.
Production wiring belongs to Task 26, CI/coverage
and upload guards to Task 31, and actual resolver/provider and publication graph
verification to their later tasks. Probe APIs/test scaffolding never become
production APIs. No merge, remote release or issue closure follows from this result.

Final review coverage: the isolated code-reviewer inspected all changed files,
the revised documentation, hosted consumer XML, producer suite totals, target
metadata/dependencies and execution/link evidence and returned **APPROVE** with
no findings or outstanding requests. External review used PAL's Claude CLI with
observed model `claude-opus-5-5` (Anthropic), also **APPROVE** after the P3 fixes.
API review was unavailable; the other eligible CLI executables were absent and
Gemini was retired. Its unchanged mechanisms/lockfile were not re-reviewed on
follow-up, and some producer totals came from the author-produced summary.
PAL stripped read-event receipts and denied attempted Bash verification, so the
external findings/approval remain attributed, without a corroboration or complete
read-coverage claim. Neither reviewer ran the builds/tests. No systemic P0/P1
finding or new convention needs indexing.

A fresh `gh run view` export retained inconsistent job `completedAt` fields
(including end times earlier than starts). These API timing fields are not used
for duration or ordering claims; the reported costs come from Gradle profiles,
and execution evidence comes from XML/task logs and successful job conclusions.
The observation is retained as an evidence limit, not a source defect or an
unmet mechanism requirement.

## Task 26: production target setup

Baseline `66d8752afc046f4fd185ae489a6d17130f1a2348`, 2026-11-23. This task adopts
Kotlin/KGP 2.3.21 in the production catalog while retaining Gradle 8.14.3,
compiler JDK 21 and JVM 11/21 execution. BCV 0.17.0, Dokka 2.0.0, Spotless 7.2.1,
NMCP 1.0.2 and the existing semantic-versioning plugins retain their pins.
The new common test integration uses kotlin.test 2.3.21, Jupiter 5.11.0 and
JUnit Platform launcher 1.11.0 alongside the existing Kotest 5.9.1 engine.

### Production boundaries

`faker-multiplatform-conventions` extends the existing library/JVM convention
and opts core into JS browser/Node, iosArm64 and iosSimulatorArm64. Other modules
retain their current JVM conventions. JS and Native are enabled by default;
the existing build properties can still disable a target family for local work.
The verification workflow explicitly enables both families and disables build-cache
reuse, checking named non-skipped common cases on the real core source sets.
Required PR integration and publication/coverage enforcement remain Task 31.

Reflection, the Kotlin JVM bundle/BOM and generated legacy resources are now
confined to JVM source sets. The jvmTest generated-resource overlay is retained
because removing it silently removed the French, Japanese and Chinese locale
discovery cases. Task 5 owns replacing that classloader-based enumeration and
removing legacy packaged resources. Jackson, ICU and RgxGen remain JVM dependencies.

`runtime/GenerationContracts.kt` contains only internal raw configuration values,
a source/category JSON-text lookup contract and a bounded-selection contract.
They contain no references to the JVM Faker/config/service/provider types. Null
resource categories select non-faker metadata; missing projections return null
for the later resolver to report. Normalization, fallback, decoding and seeded
algorithms are not implemented here. The common test verifies these actual
declarations compile and execute on each target, not provider completeness.
The public Faker facade and providers remain in jvmMain.

### Build integration corrections

The initial version-only update failed because buildSrc imported the application
Kotlin BOM, upgrading its libraries beyond Gradle's embedded compiler metadata
support (`5d9a7725-1d9f-4eea-94e4-edbe50507520`). Temporary compiler overrides
encountered the old script language defaults and K2 script frontend limitation
(`d3ec69d8-cf00-4931-b0ea-5557bf1e1cad`, `2464b5e7-5fc7-401a-945f-44dd0de2b4bd`).
They were removed. The final fix removes the application BOM from buildSrc and
retains Gradle's own compiler/library integration; configuration passed in
`8093fcb9-28e5-48e7-a52d-a6e34b0c4081`. No metadata bypass or compiler frontend
override remains. Kotlin's removed dependency-handler `platform` call is replaced
with Gradle's `project.dependencies.platform` in the JVM source set.

The Kotlin 2.3 default JVM interface mode adds seven synthetic default-argument
dispatchers to IRandom. The inspected API diff removes no prior signature and
retains IRandom.DefaultImpls; the baseline records those additions. Repository
API checks passed (`96428d40-30d0-4053-ada7-bf18f0890eeb`). This follows the
[compatible default-method mode](https://kotlinlang.org/docs/whatsnew22.html#changes-to-default-method-generation-for-interface-functions).

Central PREFER_SETTINGS policy ignored KGP's temporary Node/Yarn repositories
(`901d2108-e552-41e7-853a-f38d81768575`). The shared settings now declare the
same distribution layouts with content restricted to org.nodejs:node and
com.yarnpkg:yarn. The layouts were checked against KGP 2.3.21's source/installed
classes; the broader repository policy is unchanged. These are build tools,
not consumer dictionary downloads. The root Yarn lock records the test tool graph.

### JVM test corrections and local verification

Baseline capture `ad033c5f-d95b-4e1b-9f7a-a2c138a5decb` passed existing core
JVM minimum/maximum and repository API checks; retained XML has 4,415 cases per
JVM launcher. Upgrade verification exposed two pre-existing probabilistic
documentation assertions. The Extras PICK_RANDOMLY case wrongly required a mix
of choices; it now supplies four explicit choices and checks exact outputs.
The UniqueGenerator clearAll case assumed independently sampled name batches
must overlap; it now replays the random stream and checks both complete lists.
The library's class-generation/uniqueness behavior is unchanged. Failures remain
in captures `59f3ac7e-992b-412d-bedb-21e9b53810d6` and
`8a35eafa-2384-445c-a9cb-ec5b3cb1925b`, not discarded as successful retries.

Final core capture `3849fb60-4440-407d-aa93-c9d47a5bd4e2` passed:

- `:kotlin-faker:jvmTest` and `:kotlin-faker:jvmMaxJdkTest`: 4,423 cases each.
- `:kotlin-faker:jsNodeTest` and `:kotlin-faker:jsBrowserTest`: three cases each.
- `:kotlin-faker:compileCommonMainKotlinMetadata`, repository `apiCheck` and `spotlessCheck`.

Comparing case names with the baseline finds no missing case. The eight additional
JVM cases are the three common tests plus five existing Java Jupiter cases that
the former engine configuration did not discover. Explicit Jupiter integration
now runs those Java configuration tests on both JVM versions.

A temporary JaCoCo 0.8.16 init script, outside production configuration, measures
the executable common class: 15/15 instructions, 3/3 methods, 1/1 line and no
branch instructions. The two interfaces have no executable bodies. Capture
`00bf29bc-de9c-4829-9901-e913824c0b8f` records this focused measurement and the
subsequent successful core Dokka/CLI checks. Production Kover installation and
enforcement remain Task 31; JS/iOS coverage is not inferred from JVM measurement.

### Hosted production verification

[Run 45250478318](https://github.com/serpro69/kotlin-faker/actions/runs/45250478318)
passed both production jobs at `57156de08bd7f8d9fde6d6be6e18293d73b876de`.
The downloaded XML contains **16,333 passing cases, zero skipped/failed cases**
across 41 task reports. Capture `6f668469-2658-421b-bca8-86bca59f835e` checks every
report. Common cases executed three times in each of five environments, for
**15 actual common-test executions**. Core has 4,423 cases on each JVM launcher
and three each in Node/browser; the iOS simulator contributes three.

The Linux job ran repository-wide `jvmTest`, `jvmMaxJdkTest`, `apiCheck` and
`spotlessCheck`, along with core JS/browser and common metadata compilation.
The Apple task log records simulator compile/link/execution, device test linking
and common metadata compilation with 21 tasks executed. Both target families
were explicitly enabled and build-cache reuse disabled. The selected Apple
environment remains Xcode 26.0.1 on macos-latest Arm64.

Local tooling capture `00bf29bc-de9c-4829-9901-e913824c0b8f` also executed core
`dokkaGfmPartial` and six CLI unit tests successfully. This verifies CLI JVM
compilation/execution, not native-image creation. Native-image integration remains
Task 20/final release verification. Signing, actual production publication/BOM
consumption and remote upload/tag guard certification are not claimed here.

Final independent source/evidence review remains pending. Owner: Task 26
implementer. Close that review before marking this task done or starting Task 31.
Task 31 must make target checks required and enforce production coverage/release
guards before extraction. No remote publication, tag, merge or release is
authorized by this setup task.

### Corrections from the broader PR checks and review

The existing PR workflow [45250494766](https://github.com/serpro69/kotlin-faker/actions/runs/45250494766)
at the same candidate passed docs, API validation and native-image build/smoke
checks. Its host `check` jobs failed on inherited test assumptions: Ubuntu rejected
a valid sorted shuffle `[38, 89]` and the literal place name `Wagga Wagga`;
Windows/macOS rejected non-overlapping independently sampled name batches after
disableAll. The relevant production code/data and failed assertions were unchanged
from the Task 26 base. These failed results are retained, not counted as passes.

The correction replaces both service/provider shuffle assertions with explicit
draws, exact shuffled/unshuffled permutations and input-preservation checks.
The disableAll example now supplies a repeated draw and requires both providers
to accept it twice. The existing integration-helper exception lists now recognize
`Wagga Wagga` from `en/australia.yml` and `Lime #5` from `en/cannabis.yml`, resolving
the literal-hash issue previously assigned to Task 15. Provider/data migration
and its common-target assertions remain in their planned tasks. No dictionary
entry or production generator was changed.

Both code reviewers found no P0–P2 source defect in the initial candidate.
External P3 suggestions led to explicit `val faker = Faker()` setup in affected
Markdown examples and exclusive routing of the Node/Yarn coordinates to their
official distribution repositories. These changes retain PREFER_SETTINGS and
the same KGP distribution layouts; Maven repositories cannot supply those tools.
Focused correction capture `30f569e5-ca8b-4509-86cd-840587fd1f0a` passed JVM 11/21
random/uniqueness tests, travel suites, browser/Node and formatting checks.
Final corrected-source hosted verification and independent review are pending.

Publication wiring references: [KMP publication structure](https://kotlinlang.org/docs/multiplatform/multiplatform-publish-lib-setup.html)
and [Gradle 8.14.3 Maven publishing](https://docs.gradle.org/8.14.3/userguide/publishing_maven.html).
