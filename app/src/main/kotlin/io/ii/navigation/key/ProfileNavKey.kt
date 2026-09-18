package io.ii.navigation.key

import kotlinx.serialization.Serializable

@Serializable
sealed interface ProfileNavKey : AppNavKey

@Serializable
data object SettingsKey : ProfileNavKey
