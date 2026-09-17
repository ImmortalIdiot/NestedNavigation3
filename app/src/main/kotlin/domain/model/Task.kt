package domain.model

data class Task(
    val id: String,
    val projectId: String,
    val title: String,
    val description: String,
    val status: TaskStatus
)

enum class TaskStatus {
    TODO,
    IN_PROGRESS,
    COMPLETED
}
