package io.ii.domain.repository

import io.ii.domain.model.Task

interface TaskRepository {

    suspend fun get(projectId: String, taskId: String): Task?
}
