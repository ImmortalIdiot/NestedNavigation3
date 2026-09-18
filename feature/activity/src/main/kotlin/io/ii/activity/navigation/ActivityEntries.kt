package io.ii.activity.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.ii.activity.screen.ActivityScreen

fun EntryProviderScope<NavKey>.activityEntries() {
    entry<ActivityKey> {
        ActivityScreen()
    }
}
