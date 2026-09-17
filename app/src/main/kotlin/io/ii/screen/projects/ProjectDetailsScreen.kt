package io.ii.screen.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.model.Project
import domain.model.Task
import domain.model.TaskStatus

@Composable
fun ProjectDetailsScreen(
    project: Project,
    onTaskClick: (taskId: String) -> Unit
) {
    val completedTasks = project.tasks.count { task ->
        task.status == TaskStatus.COMPLETED
    }

    val progress = if (project.tasks.isEmpty()) {
        0f
    } else {
        completedTasks.toFloat() / project.tasks.size
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = project.title,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        item {
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "$completedTasks of ${project.tasks.size} tasks completed",
                    style = MaterialTheme.typography.labelLarge
                )

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        item {
            Text(
                text = "Tasks",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        if (project.tasks.isEmpty()) {
            item {
                Text(
                    text = "This project does not have any tasks.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(
                items = project.tasks,
                key = { task -> task.id }
            ) { task ->
                TaskCard(
                    task = task,
                    onClick = {
                        onTaskClick(task.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun TaskCard(
    task: Task,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            TaskStatusLabel(status = task.status)
        }
    }
}
