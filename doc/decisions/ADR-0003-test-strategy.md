---
id: ADR-0003
title: Test strategy
status: accepted
date: 2026-10-13
---

# ADR-0003: Test strategy

## Context

The open question was what the verification of the project consists of and which test programs exist.
The choice to be decided was how test programs are named, stored, and run.
The project still runs its test programs with JUnit 4; the framework they run with is the subject of this
decision.

## Decision

1. The test programs of the project are located under `src/test/java`.
2. Test programs run with JUnit 5; the version is managed by `ofx-bom`, and the assertions come from
   `org.junit.jupiter.api.Assertions`. No further test framework is introduced.
3. A test program is a class under `src/test/java` that carries at least one `@Test` method and whose name
   follows the selection pattern of the test run (the names `Test*` and `*Test`). A base or helper class
   without a `@Test` method is not a test program, even if its name matches the pattern.
4. A test program runs without an application container, without a database, and without a network and is
   therefore repeatable without preparation. A logging configuration under `src/test/resources` is allowed
   and does not stand in the way.
5. Time and inputs of a test program are parameters; it holds them as named constants in the test program or
   as test data under `src/test/resources`. Fixed inputs are allowed, a fixed expectation is not; it derives
   the expectation from its inputs. It does not use identifiers of the environment, such as the time zone,
   as a fixed value.
6. The verification command `mvn -q -DskipTests=false verify` runs the test programs and ends with the
   return value 0.

## Rationale

- Open: the project still runs its test programs on JUnit 4 in the modules `xml`, `util`, `core`, `wiki`,
  and `jsf`; this decision sets JUnit 5 as the target and makes the migration explicit.
- The verification command evaluates only the return value; it therefore needs test programs that determine
  success and failure themselves, and not programs that only write their output.
- The verification command runs without an application container and without a database; a program that
  needs these is therefore not a test program in the sense of this decision.
- A `main` program that uses fixed calendar dates and fixed identifiers and opens connections is not
  repeatable without preparation and is therefore likewise not a test program.
- The test programs of a module are in the test scope and do not reach its jar.

## Alternatives

- Test programs without a framework as pure `main` programs – not selected; then every program would have to
  report its success itself instead of leaving it to the framework.
- Extending the verification command to all classes under `src/test/java`, also without a name pattern – not
  selected; many of them need an application container, a database, and a network and do not determine the
  return value reliably.

## Impact

- Affected requirements: none.
- Files: the test directories `xml/src/test/java`, `util/src/test/java`, `core/src/test/java`,
  `wiki/src/test/java`, `jsf/src/test/java`, and `client/src/test/java` take the test programs.
- Files: `bom/pom.xml` and the module POMs `xml/pom.xml`, `util/pom.xml`, `core/pom.xml`, `wiki/pom.xml`,
  and `jsf/pom.xml` – open: their JUnit 4 bindings become JUnit 5 bindings.

## Open Points

- The Maven version that the build requires is not regulated by this decision; ADR-0002 governs it, and the
  root project `org.openfuxml:ofx` enforces 3.6.3 through `requireMavenVersion`.
- The version of `maven-surefire-plugin` that runs the test programs is not regulated by this decision; the
  project does not bind the plugin, so the default of the build's Maven distribution applies.
- The migration from JUnit 4 to JUnit 5 is the subject of this decision; the individual steps (POM changes
  and source changes) are not regulated by it.

## Evidence

### Implementation

- `bom/pom.xml` – open: manages JUnit 5 in the test scope for the modules; today it manages `junit:junit`
  4.13.2 (point 2)
- `xml/pom.xml`, `util/pom.xml`, `core/pom.xml`, `wiki/pom.xml`, `jsf/pom.xml` – open: bind JUnit 5 in the
  test scope; today they bind `junit:junit` (point 2)
- `core/src/test/java`, `util/src/test/java`, `wiki/src/test/java`, `jsf/src/test/java` – open: carry test
  programs that use `org.junit.jupiter.api.Test` and the JUnit 5 assertions (points 1 to 3); today they use
  `org.junit.Test`, `org.junit.Before`, and `org.junit.After`
- `xml/src/test/java` – carries the shared base classes `AbstractOfxTest` and `AbstractOfxXmlTest`, which
  the `ofx-xml` test-jar publishes and the other modules consume, and the `Test*` content builders without a
  `@Test` method; the single test program `TestXmlSection` is marked `@Ignore`; open: the base classes still
  use `org.junit.BeforeClass` (points 1 to 3)
- `client/src/test/java` – carries the `main` programs `CliOfxRenderer`, `CliWikiFetcher`, and `CliOfxDoc`
  and no test program in the sense of point 3

### Tests

- `mvn -q -DskipTests=false verify` – open: the reactor run of 2026-10-13 ends in the module `core` with
  test failures, so the return value is not 0

### Documentation

- `README.md` (`## Relevant Values`) – names the test coverage with JUnit tests and XML test resources
