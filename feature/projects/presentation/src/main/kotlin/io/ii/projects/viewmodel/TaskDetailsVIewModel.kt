package io.ii.projects.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ii.domain.model.Task
import io.ii.domain.usecase.GetTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface TaskDetailsUiState {

    data object Loading : TaskDetailsUiState

    data class Content(
        val task: Task
    ) : TaskDetailsUiState

    data object NotFound : TaskDetailsUiState

    data class Error(
        val message: String?
    ) : TaskDetailsUiState
}

class TaskDetailsViewModel(
    private val projectId: String,
    private val taskId: String,
    private val getTask: GetTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<TaskDetailsUiState>(
        TaskDetailsUiState.Loading
    )

    val uiState: StateFlow<TaskDetailsUiState> = _uiState.asStateFlow()

    init {
        loadTask()
    }

    private fun loadTask() {
        viewModelScope.launch {
            _uiState.value = TaskDetailsUiState.Loading

            _uiState.value = try {
                val task = getTask(
                    projectId = projectId,
                    taskId = taskId
                )

                if (task == null) {
                    TaskDetailsUiState.NotFound
                } else {
                    TaskDetailsUiState.Content(task)
                }
            } catch (exception: Exception) {
                TaskDetailsUiState.Error(
                    message = exception.message
                )
            }
        }
    }
}
