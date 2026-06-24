package com.example.taskie.data.repository

import android.R
import com.example.taskie.data.model.CreateTaskRequest
import com.example.taskie.data.model.CreateTaskResponse
import com.example.taskie.data.model.LoginRequest
import com.example.taskie.data.model.LoginResponse
import com.example.taskie.data.model.Task
import com.example.taskie.data.model.TaskResponse
import com.example.taskie.data.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface TaskieRepository {

    suspend fun login(request: LoginRequest): Response<LoginResponse>

    fun saveToken(token: String)

    suspend fun getTasks(): Response<TaskResponse>

    fun getTasksFlow(): Flow<List<TaskEntity>>

    suspend fun createTask(title: String, body: String): Response<CreateTaskResponse>

    suspend fun getTask(id: String): Response<Task>

    suspend fun getTaskByIdLocal(id: String): TaskEntity?

    suspend fun updateTask(id: String, title: String, body: String): Response<Unit>

    suspend fun deleteTask(id: String): Response<Unit>

    fun saveUsername(username: String)

    fun getUsername(): String?

    fun saveTaskDate(taskId: String, date: String)
    fun getTaskDate(taskId: String): String?
}


