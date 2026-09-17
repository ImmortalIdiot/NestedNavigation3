package io.ii.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import data.FakeProjectData
import io.ii.screen.NotFoundScreen
import io.ii.screen.ProjectDetailsScreen
import io.ii.screen.ProjectsScreen
import io.ii.screen.TaskDetailsScreen

@Composable
fun Navigation() {
    val backStack = rememberNavBackStack(ProjectsKey)
    val navigator = remember(backStack) {
        Navigator(backStack)
    }

    LaunchedEffect(backStack) {
        snapshotFlow { backStack.toList() }
            .collect { currentStack ->
                Log.d("NavStack", currentStack.joinToString())
            }
    }

    NavDisplay(
        backStack = backStack,
        onBack = { navigator.onBack() },
        entryProvider = entryProvider {
            entry<ProjectsKey> {
                ProjectsScreen(
                    projects = FakeProjectData.getAll(),
                    onProjectClick = { projectId ->
                        navigator.navigate(ProjectDetailsKey(projectId))
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
                            navigator.navigate(
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
    )
}
