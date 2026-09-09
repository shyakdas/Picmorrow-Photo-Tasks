<!-- Copyright (c) 2026 shyakdas -->

# Contributing to Picmorrow

Thanks for taking the time to contribute.
Picmorrow is being built as an open-source Android app with a strong focus on clean architecture, maintainable UI, and reliable test coverage.

## Before You Start

Please read these first:
- [README.md](README.md)
- [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)
- [SECURITY.md](SECURITY.md)

For larger features, open an issue or start a discussion first so direction is clear before code is written.

## Development Setup

1. Fork the repository
2. Clone your fork
3. Open the project in Android Studio
4. Use the `debug` variant for local development
5. Make sure the project builds before starting work

```bash
git clone https://github.com/<your-username>/Picmorrow-Photo-Tasks.git
cd Picmorrow-Photo-Tasks
./gradlew :app:assembleDebug
```

## Branch Naming

Create your branch from `main`.

Use clear, descriptive branch names with one of these prefixes:
- `feat/` for new features
- `fix/` for bug fixes
- `chore/` for maintenance work
- `docs/` for documentation updates
- `test/` for test-only changes
- `refactor/` for code cleanup without behavior changes

Examples:
- `feat/photo-task-capture`
- `fix/launcher-label`
- `docs/readme-improvements`
- `test/home-screen-coverage`

## Commit Format

This project uses a Conventional Commits style.

Format:

```text
type(scope): short summary
```

Examples:
- `feat(tasks): add photo task capture`
- `fix(app): correct launcher label`
- `docs(readme): update setup guide`
- `test(tasks): add task creation coverage`
- `chore(ci): tighten PR quality checks`

Recommended commit types:
- `feat`
- `fix`
- `refactor`
- `test`
- `docs`
- `chore`

## Pull Request Expectations

Before opening a PR, make sure your branch:
- builds successfully
- passes lint and static analysis
- includes tests for behavior changes
- updates snapshots for UI changes
- updates documentation when needed

PR titles should also follow Conventional Commits:

```text
feat(tasks): add photo task capture
```

## PR Checklist

- I rebased or merged the latest `main`
- My branch name follows the repository convention
- My PR title follows the commit/PR format
- I kept changes scoped to a single purpose
- I preserved architecture boundaries
- I added or updated tests for changed behavior
- I updated screenshot baselines if UI changed
- I ran the required local checks
- I updated docs, strings, or screenshots if needed
- I verified there are no new warnings introduced by my change

## Code Style Rules

- Use Kotlin and Compose best practices
- Keep code readable and explicit
- Prefer small, focused functions
- Avoid unused code, dead resources, and commented-out blocks
- Keep naming clear and domain-oriented

Do not:
- place business logic directly inside composables
- access persistence directly from UI
- mix unrelated feature code into the wrong package

## Static Analysis

Run:

```bash
./gradlew ktlintCheck detekt :app:lintDebug
```

## Testing Expectations

Add unit tests for:
- ViewModel state changes
- use case behavior
- mapping logic
- date, photo task, and formatting rules

Run:

```bash
./gradlew testDebugUnitTest
```

Compile UI tests:

```bash
./gradlew :app:compileDebugAndroidTestKotlin
```

If UI visuals change, update or add Paparazzi coverage.

```bash
./gradlew recordPaparazziDebug
./gradlew verifyPaparazziDebug
```

## Documentation Expectations

Update documentation when your change affects:
- setup or build steps
- architecture decisions
- screenshots or visible app behavior
- contributor workflows

## Security

Do not open public issues for sensitive security problems.
Use [SECURITY.md](SECURITY.md) for responsible disclosure instructions.
