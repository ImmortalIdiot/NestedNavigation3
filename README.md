# Navigation 3 Practice

An educational Android application for learning Navigation 3, type-safe navigation keys, multiple back stacks, and navigation architecture in a multi-module project.

The application is a simple project manager. Users can browse projects, open project details, and view individual tasks. It also contains Activity and Profile top-level destinations.

## Project goals

- Learn the core Navigation 3 components.
- Implement type-safe navigation with `NavKey`.
- Maintain a separate back stack for each top-level tab.
- Preserve tab state while switching between tabs.
- Implement an MRU history for top-level destinations.
- Split navigation entries between feature modules.
- Apply multi-module architecture and dependency injection.
- Separate UI, business logic, and data sources.

## Tech stack

- Kotlin 2.4.20
- Jetpack Compose
- Navigation 3 (`navigation3-runtime`, `navigation3-ui`)
- Kotlin Serialization
- AndroidX ViewModel
- Kotlin Coroutines and StateFlow
- Koin
- Gradle Kotlin DSL

## Features

The application contains three top-level tabs:

- **Projects** — project list, project details, and task details.
- **Activity** — activity feed.
- **Profile** — user information, with a future entry point to settings.

The Projects navigation branch is structured as follows:

```text
ProjectKey
└── ProjectDetailsKey(projectId)
    └── TaskDetailsKey(projectId, taskId)
```
The Activity navigation branch has only one screen - `ActivityScreen`. 

The Profile navigation branch is structured as follows:
```text
ProfileKey
└── SettingsKey
```

Each top-level tab owns a separate back stack. Switching tabs preserves their internal navigation state. Selecting the active tab again pops its stack back to the root destination.

Top-level navigation uses MRU history. Back first removes nested destinations from the current tab, then returns to the previously selected tab, and closes the application only after the navigation history has been exhausted.

## Project structure

```text
navigation-api/

feature/
├── projects/
│   ├── domain/
│   ├── data/
│   ├── presentation/
│   └── di/
├── activity/
└── profile/

app/
```

### `navigation-api`

Contains the shared navigation contracts used by feature modules:

- `AppNavKey` — the base type for every navigation key in the application.
- `TopLevelNavKey` — a marker for top-level tab keys.

These interfaces are not `sealed` because their implementations are distributed across different Gradle modules.

### `feature:projects:domain`

A platform-independent Kotlin module containing the Projects business logic:

- Models: `Project`, `Task`, `TaskStatus`;
- Repository contracts: `ProjectRepository`, `TaskRepository`;
- Use cases: `GetProjectsUseCase`, `GetProjectDetailsUseCase`, `GetTaskUseCase`.

This module has not dependencies.

### `feature:projects:data`

Contains the Projects data layer:

- Fake project and task data.
- Domain repository implementations: `ProjectRepositoryImpl`, `TaskRepositoryImpl`.

This module implements the repository contracts declared by `domain`. The fake data source can later be replaced with a database or remote API without changing the presentation layer.

### `feature:projects:presentation`

Contains the UI and navigation implementation for the Projects feature:

- `ProjectsScreen`.
- `ProjectDetailsScreen`.
- `TaskDetailsScreen`.
- ViewModels and UI state for the screens.
- `ProjectsNavKey` and its concrete destination keys.
- An entry builder that registers the Projects navigation entries.

The local `ProjectsNavKey` hierarchy can remain `sealed` because all of its implementations belong to the same feature module.

### `feature:projects:di`

An Android Library responsible for assembling the Projects dependency graph:

- Binds repository interfaces to their implementations.
- Creates use cases.
- Registers ViewModels.
- Exposes the Projects Koin module.

The DI module depends on `domain`, `data`, and `presentation`, while none of those modules depend on it.

### `feature:activity` and `feature:profile`

Contain the screens, navigation keys, and entry builders for their respective features. These features currently have no separate business or data logic, so they do not yet need their own `domain`, `data`, `presentation`, and `di` submodules.

### `app`

The application's composition root. This module:

- Starts Koin.
- Loads feature DI modules.
- Creates `NavigationState` and `Navigator`.
- Manages the back stacks of top-level tabs.
- Combines feature entry builders into a single `entryProvider`.
- Contains `MainActivity` and application theme.

## Module dependencies

```text
feature:projects:data ─────────→ feature:projects:domain
feature:projects:presentation ─→ feature:projects:domain, navigation-api

feature:projects:di ───────────→ feature:projects:domain, feature:projects:data, feature:projects:presentation

feature:activity ──────────────→ navigation-api
feature:profile ───────────────→ navigation-api

app ───────────────────────────→ navigation-api, feature:projects:di, feature:projects:presentation, feature:activity, feature:profile

```

Dependencies point from outer layers toward inner layers. The `domain` module remains the most independent part of the feature.

## Navigation keys

The shared interfaces are declared in `navigation-api`:

```kotlin
interface AppNavKey : NavKey

interface TopLevelNavKey : AppNavKey
```

Each feature owns its navigation keys. For example, the Projects feature declares:

```text
ProjectsNavKey
├── ProjectKey                 — feature root and top-level tab
├── ProjectDetailsKey          — project details
└── TaskDetailsKey             — task details
```

Concrete keys are serializable, allowing Navigation 3 to restore back stacks after configuration changes or process recreation.

## Entry builders

Each feature exposes an extension function for `EntryProviderScope<NavKey>` that maps its keys to screen content.

The `app` module combines these functions:

```kotlin
entryProvider {
    projectsEntries()
    activityEntries()
    profileEntries()
}
```

This keeps ownership of screens and entries inside their features while the `app` module handles only application-level navigation composition.


## Possible next steps

- Add a Settings destination to Profile.
- Restore the MRU tab history after process death.
- Add deep-link handling.
- Cover `Navigator` with unit tests.
- Add UI tests for navigation scenarios.
- Replace fake repositories with Room or a remote data source.
- Add loading, empty, and error states to every screen.
- Test restoration of every tab after process recreation.

## Learning scenarios

1. Open Projects → Project details → Task details, then press Back several times.
2. Open a nested Projects destination, switch to Profile, and return to Projects.
3. Select Projects → Activity → Profile and verify MRU back navigation.
4. Select the active tab again and verify that its stack returns to the root.
5. Rotate the device on a nested destination and verify that the back stack is preserved.

## Purpose

This project was created to learn Navigation 3 step by step and apply it within an architecture that resembles a production Android application.
