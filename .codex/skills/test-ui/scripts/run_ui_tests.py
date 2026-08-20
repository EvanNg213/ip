#!/usr/bin/env python3
"""Compile Chocolate and run the console tests in test/ui-test-plan.md."""

from __future__ import annotations

import re
import subprocess
import sys
import tempfile
from pathlib import Path


CASE_PATTERN = re.compile(
    r"^## Test: (?P<name>.+?)\n"
    r"Aim: (?P<aim>.+?)\n\n"
    r"### Input\n```text\n(?P<input>.*?)\n```\n\n"
    r"### Expected output\n```text\n(?P<expected>.*?)\n```",
    re.MULTILINE | re.DOTALL,
)


def normalise(text: str) -> str:
    """Make platform line endings comparable without hiding output differences."""
    return text.replace("\r\n", "\n").rstrip("\n")


def load_cases(plan_path: Path) -> list[dict[str, str]]:
    """Read the Markdown test-plan format into runnable test cases."""
    plan = plan_path.read_text(encoding="utf-8")
    cases = [match.groupdict() for match in CASE_PATTERN.finditer(plan)]
    if not cases:
        raise ValueError("No test cases found. Follow the format in test/ui-test-plan.md.")
    return cases


def compile_program(repo: Path, output_dir: Path) -> None:
    """Compile every source file required by Chocolate into a temporary directory."""
    source_files = sorted((repo / "src/main/java").glob("*.java"))
    if not source_files:
        raise FileNotFoundError("No Java source files found in src/main/java.")
    result = subprocess.run(
        ["javac", "--release", "25", "-d", str(output_dir), *map(str, source_files)],
        capture_output=True,
        text=True,
        cwd=repo,
    )
    if result.returncode != 0:
        raise RuntimeError("Compilation failed:\n" + result.stderr)


def run_case(output_dir: Path, case_input: str) -> str:
    """Run one new Chocolate process and return its standard output."""
    result = subprocess.run(
        ["java", "-cp", str(output_dir), "Chocolate"],
        input=case_input + "\n",
        capture_output=True,
        text=True,
    )
    if result.returncode != 0:
        raise RuntimeError("Chocolate stopped with an error:\n" + result.stderr)
    return result.stdout


def print_transcript(case: dict[str, str], actual: str) -> None:
    """Display the input and output from a completed console test."""
    print(f"\nTest: {case['name']}")
    print(f"Aim: {case['aim']}")
    print("Console input:")
    print(case["input"])
    print("Console output:")
    print(actual, end="" if actual.endswith("\n") else "\n")


def main() -> int:
    repo = Path(__file__).resolve().parents[4]
    plan_path = repo / "test/ui-test-plan.md"

    try:
        cases = load_cases(plan_path)
        with tempfile.TemporaryDirectory(prefix="chocolate-ui-test-") as temporary_path:
            output_dir = Path(temporary_path)
            compile_program(repo, output_dir)

            for case in cases:
                actual = run_case(output_dir, case["input"])
                print_transcript(case, actual)
                if normalise(actual) != normalise(case["expected"]):
                    print("FAIL: Actual output did not match expected output.")
                    print("Expected output:")
                    print(case["expected"])
                    print("Actual output:")
                    print(actual, end="" if actual.endswith("\n") else "\n")
                    return 1

        print(f"\nPASS: {len(cases)} UI test case(s) passed.")
        return 0
    except (FileNotFoundError, RuntimeError, ValueError) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 2


if __name__ == "__main__":
    raise SystemExit(main())
