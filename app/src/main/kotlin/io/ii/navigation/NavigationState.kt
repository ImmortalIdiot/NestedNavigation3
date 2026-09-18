package io.ii.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import io.ii.TopLevelNavKey
import io.ii.activity.navigation.ActivityKey
import io.ii.profile.navigation.ProfileKey
import io.ii.projects.navigation.ProjectKey

class NavigationState(
    val startTopLevelKey: TopLevelNavKey,
    val backStacks: Map<TopLevelNavKey, NavBackStack<NavKey>>,
    val topLevelHistory: SnapshotStateList<TopLevelNavKey>
) {
    val selectedTopLevelKey: TopLevelNavKey
        get() = topLevelHistory.last()

    val selectedBackStack: NavBackStack<NavKey>
        get() = backStacks.getValue(selectedTopLevelKey)

    val stacksInUse: List<TopLevelNavKey>
        get() = topLevelHistory.toList()
}

@Composable
fun rememberNavigationState(
    startTopLevelKey: TopLevelNavKey,
): NavigationState {
    val projectBackStack = rememberNavBackStack(ProjectKey)
    val activityBackStack = rememberNavBackStack(ActivityKey)
    val profileBackStack = rememberNavBackStack(ProfileKey)

    val backStacks: Map<TopLevelNavKey, NavBackStack<NavKey>> = remember(
        projectBackStack,
        activityBackStack,
        profileBackStack
    ) {
        mapOf(
            ProjectKey to projectBackStack,
            ActivityKey to activityBackStack,
            ProfileKey to profileBackStack
        )
    }

    val topLevelHistory = remember { mutableStateListOf(startTopLevelKey) }

    return remember(startTopLevelKey, backStacks) {
        NavigationState(
            startTopLevelKey = startTopLevelKey,
            backStacks = backStacks,
            topLevelHistory = topLevelHistory
        )
    }
}

@Composable
fun NavigationState.toEntries(
    entryProvider: (NavKey) -> NavEntry<NavKey>
): SnapshotStateList<NavEntry<NavKey>> {

    val decoratedEntries = backStacks.mapValues { (_, backStack) ->
        rememberDecoratedNavEntries(
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator()
            ),
            entryProvider = entryProvider
        )
    }

    return stacksInUse
        .flatMap { topLevelKey ->
            decoratedEntries.getValue(topLevelKey)
        }
        .toMutableStateList()
}
