---
id: ADR-0001
title: Compile all modules against the Java 8 API
status: accepted
date: 2026-10-11
---

# ADR-0001: Compile all modules against the Java 8 API

## Context

The question which compile level and which JDK bind the build was open in the project overview
(`README.md`, `## Open Fundamental Decisions`). The project overview records Java 8 as the language
level of the build and the `maven-compiler-plugin` with `source` and `target` `8` as a technical
assumption (`README.md`, `## Technical Assumptions`). The root `pom.xml` manages `maven-compiler-plugin`
in version `2.3.2` and configures `source` and `target` `8`.

- `source` and `target` select the source level and the class-file version, but the compilation uses the
  API of the JDK that runs the compiler.
- A build on JDK 11 could therefore use types and members that Java 8 does not have; the class files
  stay at version 52, and the failure appears only at runtime on Java 8.

## Decision

Every module of the reactor compiles against the Java 8 API.

- The root `pom.xml` manages `maven-compiler-plugin` in version `3.6.0` or newer and configures the
  parameter `release` with the value `8`; it drops its own parameters `source` and `target` `8`.
- While `release` is set, the compilation uses the API data of Java 8 and writes class-file version 52;
  the compiler call carries `--release 8` and no value of `source` or `target`.
- The build requires JDK 9 or newer for the `release` argument.
- The artifacts of the modules of the reactor carry class-file version 52 and use the Java 8 API only;
  the test compilation is checked in the same way.

## Rationale

- `--release` selects the API data of that Java version, so the compiler rejects types and members that
  the version does not provide.
- One value in the root `pom.xml` covers the reactor; a module with its own value states it itself.
- The failure appears while compiling instead of in the runtime of a Java 8 consumer.

## Alternatives

### `source` and `target` `8` (initial situation)

Keeps class-file version 52 but compiles against the API of the build JDK, so a Java 9 or newer API
stays unnoticed.

### Building with JDK 8

Rejects a newer API by definition, but binds the whole build to JDK 8.

### Signature check of the Java 8 API after the compilation

Checks the bytecode and works with any JDK, but adds an external component, which requires a decision of
its own, and a separate signature artifact.

## Impact

- `pom.xml` (root) – the parameter `release` replaces `source` and `target` for every module, and the managed
  `maven-compiler-plugin` carries a version of 3.6.0 or newer.
- `README.md` (`## Technical Assumptions`) – the language entry names the Java 8 API of the compilation.
- `README.md` (`## Build and Start`) – the prerequisite names JDK 9 or newer in place of JDK 8.
- `doc/requirements/architecture.md` displays the decision.
- The index (`doc/status.md`) lists the decision.

## Open Points

- A module that later needs Java 11 APIs configures its own `release` value; the decision does not
  regulate which module that is.
- The mixed `javax`/`jakarta` namespaces across the modules are not regulated by this decision
  (`README.md`, `## Open Fundamental Decisions`).
- Whether the build stays on Java 8 or is raised to a current LTS level is not regulated by this
  decision (`README.md`, `## Open Fundamental Decisions`).
- The JDK that runs the build is not regulated beyond the minimum that the `release` argument requires.

## Evidence

### Implementation

- `pom.xml` (root) – open: the parameter `release` with the value `8` in place of `source` and `target` `8`

### Tests

- `mvn -DskipTests clean install` of the reactor on JDK 11 – open: the class files carry class-file
  version 52 and use the Java 8 API only

### Documentation

- `README.md` (`## Technical Assumptions`) – open: the language entry names the Java 8 API
- `README.md` (`## Build and Start`) – open: the prerequisite names JDK 9 or newer
