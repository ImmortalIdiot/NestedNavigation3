package io.ii.di

import io.ii.data.impl.ProjectRepositoryImpl
import io.ii.data.impl.TaskRepositoryImpl
import io.ii.domain.repository.ProjectRepository
import io.ii.domain.repository.TaskRepository
import io.ii.domain.usecase.GetProjectDetailsUseCase
import io.ii.domain.usecase.GetProjectsUseCase
import io.ii.domain.usecase.GetTaskUseCase
import io.ii.projects.viewmodel.ProjectDetailsViewModel
import io.ii.projects.viewmodel.ProjectsViewModel
import io.ii.projects.viewmodel.TaskDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val projectsDiModule = module {

    single<ProjectRepository> {
        ProjectRepositoryImpl()
    }

    single<TaskRepository> {
        TaskRepositoryImpl()
    }

    factory {
        GetProjectsUseCase(
            projectRepository = get()
        )
    }

    factory {
        GetProjectDetailsUseCase(
            projectRepository = get()
        )
    }

    factory {
        GetTaskUseCase(
            taskRepository = get()
        )
    }

    viewModel {
        ProjectsViewModel(
            getProjects = get()
        )
    }

    viewModel { parameters ->
        ProjectDetailsViewModel(
            projectId = parameters.get(),
            getProjectDetails = get()
        )
    }

    viewModel { parameters ->
        TaskDetailsViewModel(
            projectId = parameters[0],
            taskId = parameters[1],
            getTask = get()
        )
    }
}
