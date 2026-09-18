package io.ii.domain.usecase

import io.ii.domain.model.Project
import io.ii.domain.repository.ProjectRepository

class GetProjectDetailsUseCase(
    private val projectRepository: ProjectRepository
) {

    suspend operator fun invoke(projectId: String): Project? = projectRepository.get(projectId)
}
