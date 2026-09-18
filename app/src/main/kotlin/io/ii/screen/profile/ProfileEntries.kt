package io.ii.screen.profile

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.ii.navigation.key.AppNavKey
import io.ii.navigation.key.ProfileKey
import io.ii.navigation.key.SettingsKey

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
