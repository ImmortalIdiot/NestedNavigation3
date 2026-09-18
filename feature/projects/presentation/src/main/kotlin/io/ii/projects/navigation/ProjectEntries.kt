package io.ii.projects.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.ii.AppNavKey
import io.ii.projects.screen.ProjectDetailsScreen
import io.ii.projects.screen.ProjectsScreen
import io.ii.projects.screen.TaskDetailsScreen
import io.ii.projects.viewmodel.ProjectDetailsViewModel
import io.ii.projects.viewmodel.ProjectsViewModel
import io.ii.projects.viewmodel.TaskDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun EntryProviderScope<NavKey>.projectEntries(
    navigate: (AppNavKey) -> Unit
) {
    entry<ProjectKey> {
        val viewModel = koinViewModel<ProjectsViewModel>()
        ProjectsScreen(
            viewModel = viewModel,
            onProjectClick = { projectId ->
                navigate(ProjectDetailsKey(projectId))
            }
        )
    }

    entry<ProjectDetailsKey> { key ->
        val viewModel = koinViewModel<ProjectDetailsViewModel> {
            parametersOf(key.projectId)
        }

        ProjectDetailsScreen(
            viewModel = viewModel,
            onTaskClick = { taskId ->
                navigate(
                    TaskDetailsKey(
                        taskId = taskId,
                        projectId = key.projectId
                    )
                )
            }
        )
    }

    entry<TaskDetailsKey> { key ->
        val viewModel = koinViewModel<TaskDetailsViewModel> {
            parametersOf(
                key.projectId,
                key.taskId
            )
        }

        TaskDetailsScreen(viewModel)
    }
}
