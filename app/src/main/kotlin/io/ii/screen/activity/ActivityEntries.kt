package io.ii.screen.activity

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.ii.navigation.key.ActivityKey

fun EntryProviderScope<NavKey>.activityEntries() {
    entry<ActivityKey> {
        ActivityScreen()
    }
}
