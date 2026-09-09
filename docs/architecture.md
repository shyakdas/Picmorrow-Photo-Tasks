# Picmorrow Architecture

Picmorrow follows MVVM with Clean Architecture and a feature-first package layout.

## Package Layout

```text
com.picmorrow
├── core
│   ├── common
│   ├── data
│   ├── database
│   ├── model
│   ├── network
│   └── ui
├── di
├── feature
│   └── phototasks
│       ├── data
│       │   ├── datasource
│       │   ├── mapper
│       │   └── repository
│       ├── domain
│       │   ├── model
│       │   ├── repository
│       │   └── usecase
│       └── presentation
│           ├── components
│           ├── model
│           └── screen
└── navigation
```

## Layer Rules

- `presentation` owns Compose screens, UI state, UI events, and ViewModels.
- `domain` owns business models, repository contracts, and use cases.
- `data` owns repository implementations, local/remote data sources, DTOs, entities, and mappers.
- `core` contains shared utilities that are not specific to a single feature.
- `navigation` owns app destinations and route wiring.
- `di` owns dependency graph bindings.

## Dependency Direction

Dependencies point inward:

```text
presentation -> domain <- data
```

The domain layer must not depend on Android framework APIs, Compose, databases, or network clients.
