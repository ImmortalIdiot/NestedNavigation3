package data

import domain.model.Project
import domain.model.Task
import domain.model.TaskStatus

object FakeProjectData {

    private val projects = listOf(
        Project(
            id = "android",
            title = "Android Application",
            description = "Develop the mobile client",
            tasks = listOf(
                Task(
                    id = "navigation",
                    projectId = "android",
                    title = "Implement Navigation 3",
                    description = "Add typed destinations and navigation.",
                    status = TaskStatus.IN_PROGRESS
                ),
                Task(
                    id = "database",
                    projectId = "android",
                    title = "Create the database",
                    description = "Configure Room and create entities.",
                    status = TaskStatus.TODO
                )
            )
        ),
        Project(
            id = "backend",
            title = "Backend API",
            description = "Develop the application API",
            tasks = listOf(
                Task(
                    id = "authentication",
                    projectId = "backend",
                    title = "Implement authentication",
                    description = "Create the authentication endpoints.",
                    status = TaskStatus.TODO
                )
            )
        )
    )

    fun getAll(): List<Project> = projects

    fun getProject(projectId: String): Project? =
        projects.find { it.id == projectId }

    fun getTask(projectId: String, taskId: String): Task? =
        getProject(projectId)
            ?.tasks
            ?.find { it.id == taskId }
}