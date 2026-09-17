package domain.model

data class Project(
    val id: String,
    val title: String,
    val description: String,
    val tasks: List<Task>,
)
