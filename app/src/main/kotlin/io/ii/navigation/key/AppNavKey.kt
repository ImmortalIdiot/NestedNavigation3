package io.ii.navigation.key

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavKey : NavKey

@Serializable
sealed interface TopLevelNavKey : AppNavKey

@Serializable
data class ProjectDetailsKey(val projectId: String) : AppNavKey

@Serializable
data class TaskDetailsKey(val taskId: String, val projectId: String) : AppNavKey
