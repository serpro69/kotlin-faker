# KMP feasibility harness

Standalone probes for dependency compatibility, common-test discovery and the
complete embedded dictionary payload. See [feasibility evidence](../../../../docs/feat/wip/kotlin-multiplatform/feasibility.md).
This build is not included in the production project or release/BOM graph.

From the repository root, with JDK 21 installed:

```sh
./gradlew -p test/fixtures/kmp-feasibility inventoryInputs jvmTest jsNodeTest
./gradlew -p test/fixtures/kmp-feasibility jsBrowserTest
./gradlew -p test/fixtures/kmp-feasibility jvmTest -PprobeTestJdk=11
```

The browser test requires Chrome; the JVM 11 command requires a discoverable
JDK 11. On an Arm64 Mac with Xcode 26.0.1 and an available iOS simulator:

```sh
./gradlew -p test/fixtures/kmp-feasibility iosSimulatorArm64Test linkDebugTestIosArm64
```

`-PprobeKotlinVersion=2.3.20` selects the provisional lower compiler for API
compatibility experiments. Keep failed runs as evidence; do not use metadata
version bypass flags. Real published-artifact consumers are added in Task 4.

Reports are under `build/test-results/` and `build/inventory/`. The inventory
uses the production SnakeYAML/ICU versions, expands YAML aliases and records
non-string scalars/keys rather than normalizing them away. Regex rows use `0` and
`A` for randomized placeholders; they describe syntax after preprocessing.

`generatePayload` runs automatically before common-source compilation. It writes
category projections and a source/locale/overlay manifest to generated Kotlin
under `build/generated/payload/commonMain/`. Never edit or commit those outputs.
Literal chunks are limited to 8,000 UTF-16 units and 24,000 JVM modified-UTF-8
bytes without splitting surrogate pairs; supplier methods avoid constant folding
and oversized initializers. The accessor decodes and caches only the requested
physical source/category. It does not resolve locale fallback or expressions.

Shared tests compare every projection with its pre-compilation UTF-16 length
and 64-bit FNV-1a fingerprint, round-trip it, and check lazy decoding, aliases,
Unicode, metadata and existing Boolean coercions. The JVM test independently
converts all authoring YAML and reconstructs it from the compiled projections,
checking every scalar, key and array position. Object key order is canonicalized.
Fingerprint expectations are generated under `commonTest`, so verification
metadata does not enlarge the main library artifact. Golden vectors check the
UTF-16 fingerprint implementation, including surrogate pairs and escaped text.
The `no` root and Spanish `NO` scalar remain `false`, exactly as in the current
production conversion; the manifest keeps logical filename locale separately.
Source-kind ranks classify locale-wide, primary and secondary files. Selection
and merging belong to Task 5: in particular, default English selects a secondary
resource instead of overlaying it on a primary resource.

After compiling targets, run `measurePayloadArtifacts` to produce
`build/payload-artifacts.tsv`. Generation sizes and a reproducible source hash
are in `build/generated/payload/manifest.json`. Use `--profile` for Gradle task
timings. Hosted runs also retain `/usr/bin/time` output; its maximum RSS is a
process measurement, not a sum of simultaneous compiler/browser memory.

No production facade, provider, fallback resolver or uniqueness implementation
belongs here. Tasks 25/3 will add the random/regex mechanism probes.
