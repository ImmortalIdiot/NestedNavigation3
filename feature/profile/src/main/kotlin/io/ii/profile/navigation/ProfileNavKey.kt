package io.ii.profile.navigation

import io.ii.AppNavKey
import io.ii.TopLevelNavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface ProfileNavKey : AppNavKey

@Serializable
data object ProfileKey : TopLevelNavKey, ProfileNavKey

@Serializable
data object SettingsKey : ProfileNavKey
