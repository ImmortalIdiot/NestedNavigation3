package io.ii.projects.navigation

import io.ii.AppNavKey
import io.ii.TopLevelNavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface ProjectsNavKey : AppNavKey

@Serializable
data object ProjectKey :  TopLevelNavKey, ProjectsNavKey

@Serializable
data class ProjectDetailsKey(val projectId: String) : ProjectsNavKey

@Serializable
data class TaskDetailsKey(val taskId: String, val projectId: String) : ProjectsNavKey
