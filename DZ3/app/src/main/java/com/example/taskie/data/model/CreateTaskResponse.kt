package com.example.taskie.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskResponse(
    val id: String? = null
)
