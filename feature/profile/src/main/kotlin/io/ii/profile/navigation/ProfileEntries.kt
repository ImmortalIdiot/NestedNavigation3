package io.ii.profile.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.ii.AppNavKey
import io.ii.profile.screen.ProfileScreen
import io.ii.profile.screen.SettingsScreen

fun EntryProviderScope<NavKey>.profileEntries(
    navigate: (AppNavKey) -> Unit
) {
    entry<ProfileKey> {
        ProfileScreen(onSettings = {
            navigate(SettingsKey)
        })
    }

    entry<SettingsKey> {
        SettingsScreen()
    }
}
