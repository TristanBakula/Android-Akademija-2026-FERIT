package com.example.note_app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val tasks: List<Task>
)

@Serializable
data class Task(
    val id: String? = null,
    val title: String? = null,
    val body: String? = null,
    val username: String? = null
)