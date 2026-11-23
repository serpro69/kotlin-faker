# KMP feasibility evidence

Task 1 inventory and toolchain checks, started 2026-11-23 at revision
`9a3844298b58c45d60e01d39cc72694163fcbdd1`. This is an execution record,
not approval of the later payload, RNG, regex, publication or migration tasks.

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
| JVM runtime range | 11–21; local 21 passed, 11 pending hosted verification. |
| JSON tree | kotlinx-serialization-json 1.11.0, no serialization compiler plugin. |
| Dates | kotlinx-datetime 0.8.0 and kotlin.time.Instant; no experimental opt-in. |
| Common tests | kotlin.test annotations/assertions; JUnit Jupiter 5.11.0 and platform launcher 1.11.0 on JVM; KGP runners on JS/Native. |
| Build-time inventory | Production versions SnakeYAML 2.2 and ICU4J 77.1. |
| Apple trial | macos-latest Arm64, explicitly selected Xcode 26.0.1, iOS 26 simulator; execution pending. |
| Provisional consumer floors | JVM and KMP: Kotlin 2.3.20; JVM and Node API probes passed locally. Apple/browser confirmation remains pending. Real artifact consumption remains Tasks 4/29/21. |
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
`FakerServiceTest` include `^AKIA\\S{16}$`, `^\\S{40}$`,
`^i-0[\\da-f]{16}$` and `^arn:.+:.+:.*:([0-9]{12}):(.+)$`; these add anchors,
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
| JVM 11 | `jvmTest -PprobeTestJdk=11` | Pending hosted verification. |
| JS Node | `jsNodeTest` | 3 tests, zero skipped/failures locally. |
| JS browser | `jsBrowserTest` | Pending hosted Chrome verification. |
| iOS simulator Arm64 | `iosSimulatorArm64Test` | Pending macOS execution. |
| iOS device Arm64 | `linkDebugTestIosArm64` | Pending compile/link of the dependency test consumer; physical-device execution is outside scope. |

Local capture `2538e203-fae9-4c53-a0af-fc712caebb48` completed successfully with
`inventoryInputs jvmTest jsNodeTest`. XML reports independently confirm three
named tests per runtime. The initial inventory attempt
`3f65fbfb-e0ae-4fba-b837-38f5d5176e27` failed on the Norwegian key, after which
the inventory was corrected to report logical and parsed keys separately.

The dedicated Actions workflow checks named, non-skipped XML cases per requested
runtime, and retains reports even on failure. No missing/disabled target counts
as success. Task 1 remains open until its hosted checks and independent review
complete. Fixture success does not validate production publication hooks, signed
uploads or API compatibility of the eventual Faker implementation.
