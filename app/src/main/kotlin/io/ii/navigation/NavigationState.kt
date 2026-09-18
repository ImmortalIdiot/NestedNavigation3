package io.ii.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

class NavigationState(
    val startTopLevelKey: TopLevelNavKey,
    val backStacks: Map<TopLevelNavKey, NavBackStack<NavKey>>
) {
    var selectedTopLevelKey by mutableStateOf(startTopLevelKey)
    val selectedBackStack: NavBackStack<NavKey>
        get() = backStacks.getValue(selectedTopLevelKey)
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

    return remember(startTopLevelKey, backStacks) {
        NavigationState(
            startTopLevelKey = startTopLevelKey,
            backStacks = backStacks
        )
    }
}
