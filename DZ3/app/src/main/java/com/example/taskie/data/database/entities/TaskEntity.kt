package com.example.taskie.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskie.data.model.Task
import kotlinx.serialization.Serializable

@Entity(tableName = "tasks")
@Serializable
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String? = null,
    val body: String? = null,
    val username: String? = null,
    val isSynced : Boolean = false
)

fun TaskEntity.toTask(): Task = Task(
    id = id,
    title = title,
    body = body,
    username = username
)