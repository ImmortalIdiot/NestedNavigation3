package io.ii.data.impl

import io.ii.data.projects
import io.ii.domain.model.Project
import io.ii.domain.repository.ProjectRepository

class ProjectRepositoryImpl : ProjectRepository {

    override suspend fun getAll(): List<Project> = projects

    override suspend fun get(id: String): Project? = projects.find { it.id == id }
}
