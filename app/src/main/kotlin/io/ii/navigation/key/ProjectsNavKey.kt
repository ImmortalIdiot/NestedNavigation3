package io.ii.navigation.key

import kotlinx.serialization.Serializable

@Serializable
sealed interface ProjectsNavKey : AppNavKey

@Serializable
data class ProjectDetailsKey(val projectId: String) : ProjectsNavKey

@Serializable
data class TaskDetailsKey(val taskId: String, val projectId: String) : ProjectsNavKey
