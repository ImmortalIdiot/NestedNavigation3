package io.ii.domain.usecase

import io.ii.domain.model.Task
import io.ii.domain.repository.TaskRepository

class GetTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(
        projectId: String,
        taskId: String
    ): Task? = taskRepository.get(projectId = projectId, taskId = taskId)
}
