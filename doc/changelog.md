# Changelog

## 2026-10-13 – Test strategy accepted

What: Set ADR-0003 (Test strategy) to accepted.
Result: The test strategy with JUnit 5 as the target is accepted; its migration remains open.
Evidence: `doc/decisions/ADR-0003-test-strategy.md` (`status: accepted`) and `doc/status.md`.
Files: `doc/decisions/ADR-0003-test-strategy.md`, `doc/status.md`, `doc/changelog.md`.

## 2026-10-13 – Test strategy decision

What: Adapted ADR-0003 (Test strategy), copied from another project, to this project and indexed it.
Result: The test strategy sets JUnit 5 as the target; the decision is proposed.
Evidence: The reactor run `mvn -q -DskipTests=false verify` fails in the module `core` (ADR-0003).
Files: `doc/decisions/ADR-0003-test-strategy.md`, `doc/status.md`, `doc/changelog.md`.

## 2026-10-11 – Maven build decision

What: Added ADR-0002 (Maven as the build tool) and created the decision index and the changelog.
Result: The build tool is fixed and the decision is indexed.
Evidence: Line-width and pattern checks: no matches found.
Files: `doc/decisions/ADR-0002-maven-build.md`, `doc/status.md`, `doc/changelog.md`.
