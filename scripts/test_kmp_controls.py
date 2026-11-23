"""Exercise migration controls in a disposable copy; upload/tag actions are always inert."""

import argparse
import json
from pathlib import Path
import shutil
import subprocess
import tempfile
import unittest
import xml.etree.ElementTree as ET

from verify_kmp_execution import TARGETS, common_test_classes, verify


class ExecutionEvidenceTest(unittest.TestCase):
    def test_requires_successful_fresh_common_cases_on_every_runner(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            evidence = root / "build/kmp-evidence"
            evidence.mkdir(parents=True)
            (evidence / "modules.json").write_text(json.dumps([
                {"path": ":kotlin-faker", "directory": "core"}]))
            sources = root / "core/src/commonTest/kotlin"
            sources.mkdir(parents=True)
            (sources / "Example.kt").write_text("package example\nclass Example { @Test fun works() {} }")
            for target, tasks in TARGETS.items():
                with self.subTest(target=target):
                    required = list(tasks)
                    if target == "ios":
                        required += ["linkDebugTestIosArm64", "compileCommonMainKotlinMetadata"]
                    if target == "coverage":
                        required += ["koverXmlReport", "verifyCommonCoverage"]
                    log = "\n".join(f"> Task :kotlin-faker:{task}" for task in required)
                    (evidence / "tasks.log").write_text(log)
                    reports = []
                    for task in tasks:
                        report = root / "core/build/test-results" / task / "TEST-example.xml"
                        report.parent.mkdir(parents=True, exist_ok=True)
                        reports.append(report)
                        report.write_text('<testsuite><testcase classname="example.Example" name="works"/></testsuite>')
                    verify(root, target)
                    for status in ("skipped", "failure", "error"):
                        reports[-1].write_text(f'<testsuite><testcase classname="example.Example" name="works"><{status}/></testcase></testsuite>')
                        with self.assertRaisesRegex(ValueError, "unsuccessful common tests"):
                            verify(root, target)
                    reports[-1].write_text('<testsuite/>')
                    with self.assertRaisesRegex(ValueError, "no executed common tests"):
                        verify(root, target)
                    reports[-1].unlink()
                    with self.assertRaisesRegex(ValueError, "no executed common tests"):
                        verify(root, target)
                    for suffix in (" SKIPPED", " NO-SOURCE", " FROM-CACHE", " UP-TO-DATE"):
                        (evidence / "tasks.log").write_text(log.replace(f":{tasks[0]}", f":{tasks[0]}{suffix}"))
                        with self.assertRaisesRegex(ValueError, "did not execute"):
                            verify(root, target)
            (evidence / "modules.json").write_text("[]")
            with self.assertRaisesRegex(ValueError, "core is missing"):
                verify(root, "jvm")


# Applied only to the disposable build. The production guard and conventions remain unchanged.
INERT_INIT = r'''
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import org.gradle.plugins.signing.Sign
gradle.projectsEvaluated {
    if (rootProject.name != 'kfaker') return
    def commonTests = new JsonSlurper().parse(new File(rootProject.projectDir, 'control-common-tests.json'))
    def inventory = []
    rootProject.allprojects { p ->
        p.version = p.findProperty('controlVersion') ?: '0.0.0'
        p.plugins.withId('maven-publish') {
            p.publishing.publications.withType(MavenPublication).configureEach { version = p.version.toString() }
            p.publishing.repositories.maven { name = 'ControlRemote'; url = uri('https://invalid.example/inert') }
        }
        // Signature generation is outside these dev/snapshot tests. Do not load user signing keys.
        p.tasks.withType(Sign).configureEach { signatory = null }
        // Negative controls exercise common code; the following production job runs full JVM suites.
        if (commonTests[p.projectDir.absolutePath]) {
            p.tasks.withType(Test).configureEach {
                filter.setIncludePatterns(commonTests[p.projectDir.absolutePath] as String[])
            }
        }
        p.tasks.each { t ->
            def types = []
            for (def c = t.class; c != null; c = c.superclass) { types.add(c.name) }
            def remote = t instanceof PublishToMavenRepository && t.repository.url.scheme != 'file'
            def nmcp = types.any { it.startsWith('nmcp.internal.task.NmcpPublish') }
            def tag = types.any { it.endsWith('.TagTask') }
            if (remote || nmcp || tag) {
                inventory.add([path: t.path, remote: remote, nmcp: nmcp, tag: tag])
                t.actions.clear()
                // Upload dependencies can create bundles/signatures, but have no role in this guard test.
                if (!tag) { t.setDependsOn([]) }
                t.doLast { new File(rootProject.projectDir, 'FORBIDDEN_ACTION').text = t.path }
                if (p.hasProperty('controlSkipUploads') && !tag) {
                    t.onlyIf { false }
                    t.enabled = false
                }
            }
        }
    }
    new File(rootProject.projectDir, 'control-tasks.json').text = JsonOutput.toJson(inventory)
}
'''


def exercise_build(root: Path, target: str) -> None:
    workspace = root / "build/kmp-controls"
    workspace.mkdir(parents=True, exist_ok=True)
    evidence_root = workspace / "evidence"
    evidence_root.mkdir(exist_ok=True)
    evidence = Path(tempfile.mkdtemp(prefix=f"{target}-", dir=evidence_root))
    with tempfile.TemporaryDirectory(prefix="candidate-", dir=workspace) as temporary:
        candidate = Path(temporary)
        ignore = shutil.ignore_patterns("build", ".gradle", ".git", ".capy", ".kotlin", "node_modules", "__pycache__")
        for name in ("buildSrc", "gradle", "core", "faker", "extension", "bom", "cli-bot", "test", "kotlin-js-store"):
            shutil.copytree(root / name, candidate / name, ignore=ignore)
        for name in ("gradlew", "gradlew.bat", "settings.gradle.kts", "build.gradle.kts", "gradle.properties"):
            shutil.copy2(root / name, candidate / name)
        # Semantic versioning needs a repository; it has no remote and no tags.
        for command in (["git", "init", "-q"], ["git", "add", "."],
                        ["git", "-c", "user.name=KMP controls", "-c", "user.email=controls@invalid.example",
                         "commit", "-qm", "Create isolated control fixture"]):
            subprocess.run(command, cwd=candidate, check=True)
        init = candidate / "inert.gradle"
        init.write_text(INERT_INIT)

        def run(label, arguments, expected=None, blocked_paths=()):
            selected = {str(source.parents[2]): sorted(common_test_classes(source.parents[2]))
                        for source in candidate.rglob("src/commonTest/kotlin")}
            (candidate / "control-common-tests.json").write_text(json.dumps(selected))
            command = [str(candidate / "gradlew"), "--console=plain", "--no-build-cache",
                       "--stacktrace", "-I", str(init), *arguments]
            log_path = evidence / f"{target}-{label}.log"
            with log_path.open("w") as log:
                result = subprocess.run(command, cwd=candidate, text=True, stdout=log,
                                        stderr=subprocess.STDOUT, timeout=1200)
            output = log_path.read_text()
            if (expected is None and result.returncode != 0) or (expected is not None and (
                    result.returncode == 0 or expected not in output)):
                raise RuntimeError(f"{label}: unexpected exit {result.returncode}; see {log_path}\n{output[-4000:]}")
            if (candidate / "FORBIDDEN_ACTION").exists():
                raise RuntimeError("An inert upload/tag action ran: the guard failed")
            if blocked_paths:
                rejection = next(line for line in output.splitlines()
                                 if line.startswith("KMP migration blocks "))
                rejected = rejection.split("authorized: ", 1)[1].split(". Use ", 1)[0].split(", ")
                if set(blocked_paths) - set(rejected):
                    raise RuntimeError(f"Guard omitted tasks: {set(blocked_paths) - set(rejected)}")
            print(f"{target}/{label}: expected exit {result.returncode}", flush=True)

        if target == "coverage":
            run("inventory", ["help"])
            inventory = json.loads((candidate / "control-tasks.json").read_text())
            (evidence / "guard-task-inventory.json").write_text(json.dumps(inventory, indent=2))
            blocked = "KMP migration blocks remote publication and release/tag tasks"
            # All registered upload/tag types, across core, dependent JVM modules and BOM.
            paths = [item["path"] for item in inventory] + [":publishToAppropriateCentralRepository"]
            if not all(any(item[kind] for item in inventory) for kind in ("remote", "nmcp", "tag")):
                raise RuntimeError("Control inventory is missing an upload/tag task type")
            run("all-release-paths", paths, blocked, paths)
            direct = [next(item["path"] for item in inventory if item["remote"])]
            direct += [item["path"] for item in inventory if item["nmcp"]]
            for index, task in enumerate(direct):
                run(f"direct-upload-{index}", [task], blocked, [task])
            run("skipped-upload-tag", [":tag", "-PcontrolSkipUploads=true"], blocked)
            run("tag-without-publisher", [":tag", "-PdryRun=true"], blocked)
            for attempt in range(2):
                run(f"cached-tag-{attempt}", [":tag", "-PdryRun=true", "--configuration-cache"], blocked)
            for version in ("0.0.0", "0.0.1-SNAPSHOT"):
                run(f"stage-{version}", [":kotlin-faker:publishJvmPublicationToRootBuildDirRepository",
                    ":kotlin-faker-bom:publishKotlinFakerBomPublicationToRootBuildDirRepository",
                    f"-PcontrolVersion={version}"])
                repository = candidate / "build/maven-repo/io/github/serpro69"
                for artifact, suffix in (("kotlin-faker-jvm", "jar"), ("kotlin-faker-bom", "pom")):
                    if not list((repository / artifact / version).glob(f"*.{suffix}")):
                        raise RuntimeError(f"Local staging produced no {artifact} {version} {suffix}")
            # A new uncalled common body must be counted automatically, without a filter update.
            control = candidate / "core/src/commonMain/kotlin/CoverageControl.kt"
            control.write_text("package control\ninternal fun untested(value: Boolean): Int = if (value) 1 else 2\n")
            run("uncovered-common-code", ["kmpCoverageCheck"], "Common coverage must be 100%")
            control.unlink()
            run("covered-common-code", ["kmpCoverageCheck"])
        else:
            control = candidate / "core/src/commonTest/kotlin/ControlFailureTest.kt"
            control.write_text('package control\nimport kotlin.test.Test\nclass ControlFailureTest {\n'
                               '@Test fun rejectsControlledFailure() { error("CONTROLLED_KMP_FAILURE") }\n}\n')
            task = {"jvm": "kmpJvmCheck", "js": "kmpJsCheck", "ios": "kmpIosCheck"}[target]
            run("failing-common-test", [task, "--continue"], "BUILD FAILED")
            log = (evidence / f"{target}-failing-common-test.log").read_text().splitlines()
            for execution in TARGETS[target]:
                if f"> Task :kotlin-faker:{execution} FAILED" not in log:
                    raise RuntimeError(f"{execution} did not fail the controlled test")
                cases = [case for report in (candidate / "core/build/test-results" / execution).glob("TEST-*.xml")
                         for case in ET.parse(report).iter("testcase")
                         if case.attrib.get("classname") == "control.ControlFailureTest"]
                if len(cases) != 1 or cases[0].find("failure") is None or "CONTROLLED_KMP_FAILURE" not in ET.tostring(cases[0], encoding="unicode"):
                    raise RuntimeError(f"{execution}: missing controlled failure in XML")
                shutil.copytree(candidate / "core/build/test-results" / execution,
                                evidence / execution)
        print(f"{target}: controls verified; no upload or tag action executed", flush=True)


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--target", choices=TARGETS, default="coverage")
    parser.add_argument("--unit-only", action="store_true")
    arguments = parser.parse_args()
    result = unittest.TextTestRunner().run(unittest.defaultTestLoader.loadTestsFromTestCase(ExecutionEvidenceTest))
    if not result.wasSuccessful():
        raise SystemExit(1)
    if not arguments.unit_only:
        exercise_build(Path(__file__).resolve().parents[1], arguments.target)
