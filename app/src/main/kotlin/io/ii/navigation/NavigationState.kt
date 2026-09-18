package io.ii.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import io.ii.TopLevelNavKey

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
    topLevelKeys: Set<TopLevelNavKey>,
    startTopLevelKey: TopLevelNavKey,
): NavigationState {
    val backStacks = rememberTopLevelBackStacks(topLevelKeys)

    val topLevelHistory = rememberSaveable { mutableStateListOf(startTopLevelKey) }

    return remember(startTopLevelKey, backStacks) {
        NavigationState(
            startTopLevelKey = startTopLevelKey,
            backStacks = backStacks,
            topLevelHistory = topLevelHistory
        )
    }
}

@Composable
private fun rememberTopLevelBackStacks(
    topLevelKeys: Set<TopLevelNavKey>
): Map<TopLevelNavKey, NavBackStack<NavKey>> {

    val backStacks = topLevelKeys.map { topLevelKey ->
        key(topLevelKey) {
            rememberNavBackStack(topLevelKey)
        }
    }

    return remember(topLevelKeys, backStacks) {
        topLevelKeys
            .zip(backStacks)
            .toMap()
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
