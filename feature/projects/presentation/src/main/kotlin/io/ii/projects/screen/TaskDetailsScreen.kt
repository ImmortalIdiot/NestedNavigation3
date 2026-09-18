package io.ii.projects.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ii.domain.model.Task
import io.ii.domain.model.TaskStatus

@Composable
fun TaskDetailsScreen(
    task: Task
) {
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = task.title,
            style = MaterialTheme.typography.headlineLarge
        )

        TaskStatusLabel(status = task.status)

        HorizontalDivider()

        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyLarge
        )

        HorizontalDivider()

        Text(
            text = "Task ID",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = task.id,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "Project ID",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = task.projectId,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun TaskStatusLabel(
    status: TaskStatus
) {
    val text = when (status) {
        TaskStatus.TODO -> "To do"
        TaskStatus.IN_PROGRESS -> "In progress"
        TaskStatus.COMPLETED -> "Completed"
    }

    val containerColor = when (status) {
        TaskStatus.TODO ->
            MaterialTheme.colorScheme.surfaceVariant

        TaskStatus.IN_PROGRESS ->
            MaterialTheme.colorScheme.primaryContainer

        TaskStatus.COMPLETED ->
            MaterialTheme.colorScheme.tertiaryContainer
    }

    Surface(
        color = containerColor,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),
            style = MaterialTheme.typography.labelMedium
        )
    }
}
