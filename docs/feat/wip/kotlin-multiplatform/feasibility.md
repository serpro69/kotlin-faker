# KMP feasibility evidence

Execution evidence for Tasks 1 and 2, started 2026-11-23 at revision
`9a3844298b58c45d60e01d39cc72694163fcbdd1`. Both tasks completed on 2026-11-23
after their hosted matrices and independent reviews passed. This does not approve
the later RNG, regex, publication or production migration tasks. The separate
inherited repository-check failure is recorded at the end; scoped feasibility
completion does not establish PR merge readiness.

Task 25's candidate sampling specification and current evidence are recorded
[below](#task-25-controlled-seeded-operations); its execution status is in tasks.md.

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
| Provisional consumer floors | JVM and KMP: Kotlin 2.3.20; API probes passed on JVM, browser, Node and iOS simulator. Real artifact consumption remains Tasks 4/29/21. |
| Minimum resolved stdlib | 2.3.20 for the consumer-floor probe; producer defaults to 2.3.21. Native uses the stdlib bundled with the selected compiler. No forced lower stdlib or metadata bypass. |
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
