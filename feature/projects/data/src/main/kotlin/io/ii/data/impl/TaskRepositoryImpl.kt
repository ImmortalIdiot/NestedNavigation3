package io.ii.data.impl

import io.ii.data.projects
import io.ii.domain.model.Task
import io.ii.domain.repository.TaskRepository

class TaskRepositoryImpl : TaskRepository {

    override suspend fun get(
        projectId: String,
        taskId: String
    ): Task? =
        projects.find { it.id == projectId }
            ?.tasks
            ?.find { it.id == taskId }
}
