"""Require fresh production common-test execution for each opted-in module (Python 3.9+)."""

import argparse
import json
from pathlib import Path
import re
import xml.etree.ElementTree as ET


TARGETS = {
    "jvm": ("jvmTest", "jvmMaxJdkTest"),
    "js": ("jsNodeTest", "jsBrowserTest"),
    "ios": ("iosSimulatorArm64Test",),
    "coverage": ("jvmTest", "jvmMaxJdkTest"),
}


def common_test_classes(directory: Path) -> set[str]:
    """Find the ordinary kotlin.test classes used by the repository's common suites."""
    classes = set()
    for source in (directory / "src/commonTest/kotlin").rglob("*.kt"):
        text = source.read_text()
        if not re.search(r"@Test\b", text):
            continue
        package = re.search(r"(?m)^package\s+([\w.]+)", text)
        prefix = package.group(1) + "." if package else ""
        classes.update(prefix + name for name in re.findall(r"\bclass\s+(\w+)", text))
    return classes


def verify(root: Path, target: str) -> None:
    evidence = root / "build/kmp-evidence"
    modules = json.loads((evidence / "modules.json").read_text())
    if not modules or not any(module["path"] == ":kotlin-faker" for module in modules):
        raise ValueError("Production core is missing from the portable-module plan")
    executed = set((evidence / "tasks.log").read_text().splitlines())
    for module in modules:
        directory = root / module["directory"]
        # Common tests use ordinary kotlin.test classes; inspect declarations, not a fixed smoke list.
        classes = common_test_classes(directory)
        if not classes:
            raise ValueError(f"{module['path']}: no common test classes")
        required = list(TARGETS[target])
        if target == "ios":
            required += ["linkDebugTestIosArm64", "compileCommonMainKotlinMetadata"]
        if target == "coverage":
            required += ["koverXmlReport", "verifyCommonCoverage"]
        for task in required:
            task_path = f"{module['path']}:{task}"
            if f"> Task {task_path}" not in executed:
                raise ValueError(f"{task_path}: did not execute (skipped/cached/missing)")
        for task in TARGETS[target]:
            cases = [case for path in (directory / "build/test-results" / task).glob("TEST-*.xml")
                     for case in ET.parse(path).iter("testcase")
                     if case.attrib.get("classname") in classes]
            if not cases:
                raise ValueError(f"{module['path']}:{task}: no executed common tests")
            bad = [case.attrib["name"] for case in cases
                   if any(case.find(status) is not None for status in ("skipped", "failure", "error"))]
            if bad:
                raise ValueError(f"{module['path']}:{task}: unsuccessful common tests: {bad}")
            print(f"{module['path']}:{task}: {len(cases)} executed common cases")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("target", choices=TARGETS)
    parser.add_argument("--root", type=Path, default=Path.cwd())
    arguments = parser.parse_args()
    verify(arguments.root, arguments.target)
