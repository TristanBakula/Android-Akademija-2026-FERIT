package com.example.taskie.data.model

import kotlinx.serialization.Serializable
import com.example.taskie.data.model.Task

@Serializable
data class TaskResponse(
    val tasks: List<Task>
)

