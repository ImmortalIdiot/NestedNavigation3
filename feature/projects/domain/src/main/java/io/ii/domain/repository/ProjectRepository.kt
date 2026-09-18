package io.ii.domain.repository

import io.ii.domain.model.Project
import io.ii.domain.model.Task

interface ProjectRepository {

    suspend fun getAll(): List<Project>

    suspend fun get(id: String): Project?
}
