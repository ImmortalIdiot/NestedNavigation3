package io.ii.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import data.FakeProjectData
import io.ii.screen.NotFoundScreen
import io.ii.screen.activity.ActivityScreen
import io.ii.screen.profile.ProfileScreen
import io.ii.screen.projects.ProjectDetailsScreen
import io.ii.screen.projects.ProjectsScreen
import io.ii.screen.projects.TaskDetailsScreen

@Composable
fun Navigation() {

    val navState = rememberNavigationState(startTopLevelKey = ProjectKey)

    val navigator = remember(navState.selectedBackStack) {
        Navigator(navState.selectedBackStack)
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 50.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        navState.selectedTopLevelKey = ProjectKey
                    }
                ) {
                    Text("Projects")
                }
                Button(
                    onClick = {
                        navState.selectedTopLevelKey = ActivityKey
                    }
                ) {
                    Text("Activity")
                }
                Button(
                    onClick = {
                        navState.selectedTopLevelKey = ProfileKey
                    }
                ) {
                    Text("Profile")
                }
            }
        }
    ) { paddings ->
        NavDisplay(
            modifier = Modifier.padding(paddings),
            backStack = navState.selectedBackStack,
            onBack = { navigator.onBack() },
            entryProvider = entryProvider {
                entry<ProjectKey> {
                    ProjectsScreen(
                        projects = FakeProjectData.getAll(),
                        onProjectClick = { projectId ->
                            navigator.navigate(ProjectDetailsKey(projectId))
                        }
                    )
                }

                entry<ActivityKey> {
                    ActivityScreen()
                }

                entry<ProfileKey> {
                    ProfileScreen(onSettings = {})
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
}
