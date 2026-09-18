package io.ii.domain.usecase

import io.ii.domain.model.Project
import io.ii.domain.repository.ProjectRepository

class GetProjectsUseCase(
    private val projectRepository: ProjectRepository
) {

    suspend operator fun invoke(): List<Project> = projectRepository.getAll()
}
