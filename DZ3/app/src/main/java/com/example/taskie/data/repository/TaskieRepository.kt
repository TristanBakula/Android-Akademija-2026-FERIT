package com.example.note_app.data.repository

import android.R
import com.example.note_app.data.model.CreateTaskRequest
import com.example.note_app.data.model.CreateTaskResponse
import com.example.note_app.data.model.LoginRequest
import com.example.note_app.data.model.LoginResponse
import com.example.note_app.data.model.Task
import com.example.note_app.data.model.TaskResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface TaskieRepository {

    suspend fun login(request: LoginRequest): Response<LoginResponse>

    fun saveToken(token: String)

    suspend fun getTasks(): Response<TaskResponse>

    suspend fun createTask(title: String, body: String): Response<CreateTaskResponse>

    suspend fun getTask(id: String): Response<Task>

    suspend fun updateTask(id: String, title: String, body: String): Response<Unit>

    suspend fun deleteTask(id: String): Response<Unit>

    fun saveUsername(username: String)

    fun getUsername(): String?

    fun saveTaskDate(taskId: String, date: String)
    fun getTaskDate(taskId: String): String?
}


