package io.ii.navigation

class Navigator(
    private val state: NavigationState
) {
    fun navigate(navKey: AppNavKey) {

        require(navKey !is TopLevelNavKey)

        if (state.selectedBackStack.lastOrNull() != navKey) {
            state.selectedBackStack.add(navKey)
        }
    }

    fun selectTopLevel(key: TopLevelNavKey) {
        if (key == state.selectedTopLevelKey) {
            while (state.selectedBackStack.size > 1) {
                state.selectedBackStack.removeAt(state.selectedBackStack.lastIndex)
            }
        } else {
            state.selectedTopLevelKey = key
        }
    }

    fun onBack(): Boolean {
        if (state.selectedBackStack.size > 1) {
            state.selectedBackStack.removeAt(state.selectedBackStack.lastIndex)
            return true
        }

        if (state.selectedTopLevelKey != state.startTopLevelKey) {
            state.selectedTopLevelKey = state.startTopLevelKey
            return true
        }

        return false
    }
}
