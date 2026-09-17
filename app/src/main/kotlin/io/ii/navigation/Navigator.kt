package io.ii.navigation

import androidx.navigation3.runtime.NavKey

class Navigator(
    private val backStack: MutableList<NavKey>
) {
    fun navigate(navKey: AppNavKey) {
        if (backStack.lastOrNull() != navKey) {
            backStack.add(navKey)
        }
    }

    fun onBack(): Boolean {
        if (backStack.size <= 1) {
            return false
        }

        backStack.removeAt(backStack.lastIndex)
        return true
    }
}
