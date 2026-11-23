# KMP feasibility harness

Standalone Task 1 probes for dependency compatibility, common-test discovery and
the existing dictionary corpus. See [feasibility evidence](../../../../docs/feat/wip/kotlin-multiplatform/feasibility.md).
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

`-PprobeKotlinVersion=2.2.21` selects the provisional lower compiler for API
compatibility experiments. Keep failed runs as evidence; do not use metadata
version bypass flags. Real published-artifact consumers are added in Task 4.

Reports are under `build/test-results/` and `build/inventory/`. The inventory
uses the production SnakeYAML/ICU versions, expands YAML aliases and records
non-string scalars/keys rather than normalizing them away. Regex rows use `0` and
`A` for randomized placeholders; they describe syntax after preprocessing.

No production facade, provider, fallback resolver or uniqueness implementation
belongs here. Tasks 2/25/3 add only the data/random/regex mechanism probes.
