package io.ii.screen.projects

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import data.FakeProjectData
import io.ii.navigation.key.AppNavKey
import io.ii.navigation.key.ProjectDetailsKey
import io.ii.navigation.key.ProjectKey
import io.ii.navigation.key.TaskDetailsKey
import io.ii.screen.NotFoundScreen

fun EntryProviderScope<NavKey>.projectEntries(
    navigate: (AppNavKey) -> Unit
) {
    entry<ProjectKey> {
        ProjectsScreen(
            projects = FakeProjectData.getAll(),
            onProjectClick = { projectId ->
                navigate(ProjectDetailsKey(projectId))
            }
        )
    }

    entry<ProjectDetailsKey> { key ->
        val project = FakeProjectData.getProject(key.projectId)

        if (project == null) {
            NotFoundScreen("Project not found")
        } else {
            ProjectDetailsScreen(
                project = project,
                onTaskClick = { taskId ->
                    navigate(
                        TaskDetailsKey(
                            taskId = taskId,
                            projectId = project.id
                        )
                    )
                }
            )
        }
    }

    entry<TaskDetailsKey> { key ->
        val task = FakeProjectData.getTask(
            projectId = key.projectId,
            taskId = key.taskId
        )

        if (task == null) {
            NotFoundScreen("Task not found")
        } else {
            TaskDetailsScreen(task)
        }
    }
}
