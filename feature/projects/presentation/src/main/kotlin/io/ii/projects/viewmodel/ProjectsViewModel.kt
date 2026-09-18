package io.ii.projects.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ii.domain.model.Project
import io.ii.domain.usecase.GetProjectsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProjectsUiState {

    data object Loading : ProjectsUiState

    data class Content(
        val projects: List<Project>
    ) : ProjectsUiState

    data class Error(
        val message: String?
    ) : ProjectsUiState
}

class ProjectsViewModel(
    private val getProjects: GetProjectsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProjectsUiState>(
        ProjectsUiState.Loading
    )

    val uiState: StateFlow<ProjectsUiState> = _uiState.asStateFlow()

    init {
        loadProjects()
    }

    private fun loadProjects() {
        viewModelScope.launch {
            _uiState.value = ProjectsUiState.Loading

            _uiState.value = try {
                ProjectsUiState.Content(
                    projects = getProjects()
                )
            } catch (exception: Exception) {
                ProjectsUiState.Error(
                    message = exception.message
                )
            }
        }
    }
}
