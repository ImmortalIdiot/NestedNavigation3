package io.ii.navigation

import io.ii.navigation.key.AppNavKey
import io.ii.navigation.key.TopLevelNavKey

class Navigator(
    private val state: NavigationState
) {
    fun navigate(navKey: AppNavKey) {

        require(navKey !is TopLevelNavKey) {
            "Use 'selectTopLevel()' for top-level navigation"
        }

        if (state.selectedBackStack.lastOrNull() != navKey) {
            state.selectedBackStack.add(navKey)
        }
    }

    fun selectTopLevel(key: TopLevelNavKey) {
        if (key == state.selectedTopLevelKey) {
            while (state.selectedBackStack.size > 1) {
                state.selectedBackStack.popLast()
            }

            return
        }

        if (key == state.startTopLevelKey) {
            state.topLevelHistory.clear()
            state.topLevelHistory.add(key)
            return
        }

        state.topLevelHistory.remove(key)
        state.topLevelHistory.add(key)
    }

    fun onBack(): Boolean {
        if (state.selectedBackStack.size > 1) {
            state.selectedBackStack.popLast()
            return true
        }

        if (state.topLevelHistory.size > 1) {
            state.topLevelHistory.popLast()

            return true
        }

        return false
    }
}

private fun <T> MutableList<T>.popLast(): T {
    return removeAt(lastIndex)
}
