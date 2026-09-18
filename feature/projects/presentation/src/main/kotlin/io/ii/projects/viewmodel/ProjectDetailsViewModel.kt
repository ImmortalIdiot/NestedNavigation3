package io.ii.projects.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ii.domain.model.Project
import io.ii.domain.usecase.GetProjectDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProjectDetailsUiState {

    data object Loading : ProjectDetailsUiState

    data class Content(
        val project: Project
    ) : ProjectDetailsUiState

    data object NotFound : ProjectDetailsUiState

    data class Error(
        val message: String?
    ) : ProjectDetailsUiState
}

class ProjectDetailsViewModel(
    private val projectId: String,
    private val getProjectDetails: GetProjectDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProjectDetailsUiState>(
        ProjectDetailsUiState.Loading
    )

    val uiState: StateFlow<ProjectDetailsUiState> = _uiState.asStateFlow()

    init {
        loadProject()
    }

    private fun loadProject() {
        viewModelScope.launch {
            _uiState.value = ProjectDetailsUiState.Loading

            _uiState.value = try {
                val project = getProjectDetails(projectId)

                if (project == null) {
                    ProjectDetailsUiState.NotFound
                } else {
                    ProjectDetailsUiState.Content(project)
                }
            } catch (exception: Exception) {
                ProjectDetailsUiState.Error(
                    message = exception.message
                )
            }
        }
    }
}
