# openFuXML

openFuXML (OFX) is a Java framework for single-source authoring and publishing of technical documentation. Content is written once in the neutral **OFX XML** markup and rendered from there into the required output format.

## Initial Situation

Technical documentation has to be published in several formats at the same time: as a printable PDF, as a web page, as a wiki article, and increasingly as a Word document. Keeping a separate source document per format duplicates the content, multiplies the maintenance effort, and lets the parallel versions drift apart. As soon as the same text appears in a manual, a help page, and a wiki, keeping them consistent becomes the dominant work.

openFuXML follows the single-source approach: the content is authored once in the format-neutral OFX XML markup, and the renderers derive every target format from that single source. The project continues the historic openFuXML; the legacy implementation is kept under `old/` for reference but is no longer part of the build.

## Relevant Values

The values that guide the project are visible in the source code and the build:

- **Single source of truth** — one `ofx:document` per topic; every output format is derived from it.
- **Separation of content and output** — the content model (`xml`) is kept apart from the renderers (`core`), so a new target format can be added without changing the content model.
- **Traceable rendering** — every render run is driven by an explicit `cmp` configuration and reports the files it produces (`OfxRenderer`, `OfxRenderConfiguration`).
- **Test coverage** — every renderer has a JUnit 5 test with XML test resources (`core/src/test`, `xml/src/test`).
- **Open source** — released under the GNU General Public License v3 and published to Maven Central.

## Technical Assumptions

- **Language and build:** Java at language level 8 (`maven-compiler-plugin`, `source`/`target` 8), built with Maven as a multi-module reactor.
- **Encoding:** UTF-8 throughout (`project.build.sourceEncoding`).
- **Base infrastructure:** the eXLP library (`net.sf.exlp`) supplies the logging bootstrap, configuration loading, and JAXB utilities; its versions are imported through `exlp-bom`.
- **Content model:** the OFX XML model is generated from XSD (`xml/src/main/xsd`) with JAXB (`javax.xml.bind` 2.3.1). The namespaces are `http://www.openfuxml.org` plus `/text`, `/media`, `/layout`, `/table`, `/list`, `/editorial`, `/addon/wiki`, and `/addon/graph`.
- **Rendering configuration:** render targets are declared in the `cmp` namespace; the configured targets are `pdf` and `html` (`xml/src/main/xsd/renderer/cmp.xsd`).
- **Output libraries:** LaTeX for PDF, Apache Batik 1.18 for SVG, Apache POI 5.2.5 and Aspose.Words 20.8 (`jdk17`) for DOCX, jsoup for HTML, and jwbf 3.1.1 for MediaWiki.
- **Non-public dependency:** Aspose.Words is a commercial library and is fetched from the Aspose Maven repository.
- **No database:** all input and configuration is file-based, namely OFX XML sources and `cmp` configuration files.

## Open Fundamental Decisions

- Whether the commercial Aspose.Words path for DOCX output is kept or replaced entirely by the Apache POI path.
- How wiki authentication is implemented; the pre-processor currently marks it as `NYI` (`OfxPreProcessor`).
- Whether the legacy modules under `old/` (server, Swing/SWT client, EPUB add-on) are migrated into the reactor or dropped for good.
- Whether the build stays on Java 8 or is raised to a current LTS level.
- Whether the mixed `javax`/`jakarta` namespaces across the modules (for example `interfaces` uses `jakarta.inject`) are unified.

## Decided Fundamental Decisions

- The build is a Maven multi-module reactor, and version management is centralized in the `bom` module, which imports `exlp-bom`.
- The XML model is generated from XSD with JAXB using the `cxf-xjc-plugin` (profile `javax`).
- The content model and the renderers are separate modules (`xml` and `core`).
- DOCX output is produced by two independent paths: Apache POI and Aspose.Words.
- The license is GNU GPL v3, and artifacts are published to Maven Central via the `central-publishing-maven-plugin`.
- The legacy code under `old/` is deactivated and excluded from the reactor.

## Planned Milestones

- Finish and stabilize the Markdown renderer (`core/src/main/java/org/openfuxml/renderer/markdown`), including tables.
- Consolidate the DOCX/Word output onto a single rendering path.
- Implement wiki authentication.
- Resolve the legacy modules under `old/`.
- Populate the requirements and decision documentation (`doc/requirements/`, `doc/status.md`, `doc/decisions/`, `doc/changelog.md`) that is referenced under **Rules and Roles**.
- Release the current snapshot `1.3.12` to Maven Central.

## Build and Start

Prerequisites: JDK 8 or newer, Maven, and network access to the Sonatype snapshot repository and the Aspose repository.

Build the whole reactor from the repository root:

```bash
mvn clean install
```

The reactor contains the modules `bom`, `interfaces`, `xml`, `util`, `doc`, `core`, `wiki`, `jsf`, and `client`. The directories `archetypes/` and `old/` are not part of the build.

Render a document. The command-line entry point is `org.openfuxml.renderer.OfxRenderer`; it requires a `cmp` configuration file and can additionally run the pre-processor:

```bash
java -cp <classpath> org.openfuxml.renderer.OfxRenderer -cmp <path-to-cmp.xml>
java -cp <classpath> org.openfuxml.renderer.OfxRenderer -cmp <path-to-cmp.xml> -pre
```

Options: `-cmp <FILENAME>` (required, the `cmp` configuration), `-pre` (run the pre-processor), `-debug` (debug output), and `-help` (print help).

For a quick demonstration, example entry points are available, for instance `org.openfuxml.CliExampleContentProvider` (builds demo content) and `org.openfuxml.test.renderer.CliOfxRenderer` (renders through a `cmp` file).

## Development Principles

- Follow the project instructions for the AI assistant in `doc/llm.md` and the **Requirements Rules** in `doc/requirements/requirements.md`.
- Write documents, source code, and new file names in **English**; use ASCII kebab-case for new file names.
- Cover new functionality with tests and run them (together with the Git diff) after each change.
- Never create a commit automatically; see the **Commit Message Rule**.

## Rules and Roles

- Project instructions for the AI assistant: `doc/llm.md`; rule files of the AI tool: `.clinerules/`.
- Commit Message Rule: `.clinerules/10-git-commit.md`.
- Requirements Rules: `doc/requirements/requirements.md`.
- Index File of requirements and architecture decisions including status: `doc/status.md`.
- Change Log (chronicle): `doc/changelog.md`.
- Architecture File: `doc/requirements/architecture.md`; Decision Repository: `doc/decisions/`.

