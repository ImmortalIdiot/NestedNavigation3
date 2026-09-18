package io.ii.navigation.key

import kotlinx.serialization.Serializable

@Serializable
sealed interface TopLevelNavKey : AppNavKey

@Serializable
data object ProjectKey : TopLevelNavKey, ProjectsNavKey

@Serializable
data object ActivityKey : TopLevelNavKey

@Serializable
data object ProfileKey : TopLevelNavKey, ProfileNavKey
