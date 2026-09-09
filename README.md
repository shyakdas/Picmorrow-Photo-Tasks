<!-- Copyright (c) 2026 shyakdas -->

# Picmorrow

[![PR Checks](https://github.com/shyakdas/Picmorrow-Photo-Tasks/actions/workflows/pr-quality-checks.yml/badge.svg)](https://github.com/shyakdas/Picmorrow-Photo-Tasks/actions)
[![Nightly Build](https://github.com/shyakdas/Picmorrow-Photo-Tasks/actions/workflows/nightly.yml/badge.svg)](https://github.com/shyakdas/Picmorrow-Photo-Tasks/actions)
[![Dependabot](https://img.shields.io/badge/dependabot-enabled-brightgreen)](https://github.com/shyakdas/Picmorrow-Photo-Tasks/security/dependabot)
[![Coverage](https://codecov.io/gh/shyakdas/Picmorrow-Photo-Tasks/branch/main/graph/badge.svg)](https://codecov.io/gh/shyakdas/Picmorrow-Photo-Tasks)

Picmorrow is an open-source Android app for turning photos into clear, lightweight tasks.

- Brand name: `Picmorrow`
- Play Store title: `Picmorrow: Photo Tasks`
- Launcher label: `Picmorrow`

## Project Overview

Picmorrow is for users who want:
- a quick way to capture photo-based tasks
- a clean Android experience built with Jetpack Compose
- local-first foundations that can grow into reminders, organization, and task workflows
- an open-source codebase with clear contribution standards

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- ktlint
- detekt
- Jacoco
- Paparazzi
- GitHub Actions

Android configuration:
- `compileSdk = 36`
- `targetSdk = 36`
- `minSdk = 30`

## Architecture

Picmorrow currently starts as a single-module app:

```text
com.picmorrow
├── MainActivity
└── ui
    └── theme
```

Expected feature boundaries as the app grows:
- `presentation`: Compose UI, route wiring, ViewModels, UI state
- `domain`: use cases and feature models
- `data`: repositories, local persistence, integrations

## Setup

### Requirements

- Android Studio latest stable
- JDK 21 for CI parity
- Android SDK 36

### Clone

```bash
git clone https://github.com/shyakdas/Picmorrow-Photo-Tasks.git
cd Picmorrow-Photo-Tasks
```

### Open in Android Studio

1. Open the project folder
2. Sync Gradle
3. Select the `debug` variant for local work
4. Run the app on an emulator or device

### Build from Terminal

```bash
./gradlew :app:assembleDebug
```

## Running Tests

### Static Analysis

```bash
./gradlew ktlintCheck detekt :app:lintDebug
```

### Unit Tests

```bash
./gradlew testDebugUnitTest
```

### UI Test Compilation

```bash
./gradlew :app:compileDebugAndroidTestKotlin
```

### Screenshot Tests

Record snapshots:

```bash
./gradlew recordPaparazziDebug
```

Verify snapshots:

```bash
./gradlew verifyPaparazziDebug
```

### Coverage

```bash
./gradlew jacocoTestReport
```

## CI

GitHub Actions in this repository run:
- PR quality checks
- PR title and description validation
- nightly validation
- signed APK build on `main`
- Dependabot dependency update checks

Workflows:
- `.github/workflows/pr-quality-checks.yml`
- `.github/workflows/pr-rules.yml`
- `.github/workflows/nightly.yml`
- `.github/workflows/build-apk-main.yml`

Signed release builds require these repository secrets:
- `RELEASE_KEYSTORE_BASE64`
- `RELEASE_KEYSTORE_PASSWORD`
- `RELEASE_KEY_ALIAS`
- `RELEASE_KEY_PASSWORD`

## Repository Rules

Recommended GitHub branch protection for `main`:
- require pull requests before merging
- require at least one approving review
- require CODEOWNERS review
- require status checks to pass before merging
- require branches to be up to date before merging
- require conversation resolution before merging
- block force pushes
- block deletions

Recommended required checks:
- `Validate PR title & description`
- `Lint & Static Analysis`
- `Unit Tests & Coverage`
- `Snapshot Tests`

## Contributing

Contributions are welcome, but consistency matters.

Before opening a PR:
- keep architecture boundaries intact
- add or update tests for behavior changes
- keep lint, detekt, and snapshot verification green
- include screenshots for visible UI changes

Project contribution docs:
- [CONTRIBUTING.md](CONTRIBUTING.md)
- [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)
- [SECURITY.md](SECURITY.md)

## License

This project is licensed under the MIT License.
See the [LICENSE](LICENSE) file for details.
