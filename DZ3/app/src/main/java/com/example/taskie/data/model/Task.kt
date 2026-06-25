package com.example.taskie.data.model


import androidx.room.PrimaryKey
import com.example.taskie.data.database.entities.TaskEntity
import kotlinx.serialization.Serializable


@Serializable
data class Task(
    val id: String? = null,
    val title: String? = null,
    val body: String? = null,
    val username: String? = null,
    val isCompleted : Boolean = false
)


fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = this.id ?: "",
        title = this.title,
        body = this.body,
        username = this.username,
        isCompleted = this.isCompleted
    )
}