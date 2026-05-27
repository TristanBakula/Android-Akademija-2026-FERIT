package com.example.note_app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskResponse(
    val id: String? = null
)
