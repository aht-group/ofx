---
id: ADR-0002
title: Maven as the build tool
status: accepted
date: 2026-10-11
affects: []
related: [ADR-0001]
---

# ADR-0002: Maven as the build tool

## Context

The question to be decided is: which build tool builds the modules, resolves the dependencies, and binds the
parent and the BOMs.

- The reactor is the root project `ofx` with the modules `bom`, `interfaces`, `xml`, `util`, `doc`, `core`,
  `wiki`, `jsf`, and `client`.
- The dependencies are the artifacts of the reactor and external libraries such as eXLP, Apache FOP,
  Apache Batik, Apache POI, Aspose.Words, jsoup, and jwbf.

## Decision

1. Maven builds the project; the root project `ofx` with packaging `pom` leads the modules `bom`, `interfaces`,
   `xml`, `util`, `doc`, `core`, `wiki`, `jsf`, and `client` in one reactor.
2. Every module uses packaging `jar`; only `bom` uses packaging `pom`.
3. The version management is centralized in the `bom` module (`ofx-bom`): it manages the artifacts of the
   reactor and imports the BOM `exlp-bom` (`net.sf.exlp`); every module imports `ofx-bom` through
   `dependencyManagement`.
4. The reactor compiles against the Java 8 API; the compile level and the compiler plugin parameters are
   governed by ADR-0001.
5. The `release` profile builds the source and Javadoc jars and publishes the artifacts to Maven Central
   through `central-publishing-maven-plugin`, signed with `maven-gpg-plugin`.
6. The build requires Maven 3.6.3 or newer; the minimum is bound in the root `pom.xml` with the
   `maven-enforcer-plugin` rule `requireMavenVersion`.

## Rationale

- The reactor and the central `bom` module map the module structure of the project onto Maven and keep the
  version management in one place.
- The compile level and the publication are bound in the reactor, so no module needs its own build execution.
- Gradle would break the layout of the existing POMs and the central `bom`.

## Alternatives

- Gradle - not chosen; the module structure, the central `bom`, and the external artifacts are laid out for
  Maven.

## Impact

- Affected requirements: none.
- Files: `pom.xml` and the module POMs of the named modules.

## Open Points

None.

## Evidence

### Implementation

- `pom.xml` - root project `ofx`, packaging `pom`, nine `<module>` entries
- `bom/pom.xml` - packaging `pom`, managed artifacts of the reactor, import of `exlp-bom`
- `<module>/pom.xml` - packaging `jar` for `interfaces`, `xml`, `util`, `doc`, `core`, `wiki`, `jsf`, `client`
- `pom.xml` - `central-publishing-maven-plugin`; in the `release` profile `maven-source-plugin`,
  `maven-javadoc-plugin`, and `maven-gpg-plugin`
- `pom.xml` - open: `maven-enforcer-plugin` with the rule `requireMavenVersion` and the value `3.6.3`

### Documentation

- `README.md` - names Maven (`## Technical Assumptions`, `## Decided Fundamental Decisions`)
