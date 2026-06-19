package com.example.note_app.data.repository

import com.example.note_app.data.SessionManager
import com.example.note_app.data.model.CreateTaskRequest
import com.example.note_app.data.model.CreateTaskResponse
import com.example.note_app.data.model.LoginRequest
import com.example.note_app.data.model.LoginResponse
import com.example.note_app.data.model.Task
import com.example.note_app.data.model.TaskResponse
import com.example.note_app.data.network.RetrofitTaskieInstance
import retrofit2.Response

class RetrofitTaskieRepository(
    private val sessionManager: SessionManager
) : TaskieRepository {

    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return RetrofitTaskieInstance.api.login(request)
    }

    override fun saveToken(token: String) = sessionManager.saveToken(token)

    override suspend fun getTasks(): Response<TaskResponse> {
        val token = sessionManager.getToken()
        return RetrofitTaskieInstance.api.getTasks("Bearer $token")
    }

    override suspend fun createTask(title: String, body: String): Response<CreateTaskResponse> {
        val token = sessionManager.getToken()
        return RetrofitTaskieInstance.api.createTask("Bearer $token",CreateTaskRequest(title, body))
    }

    override suspend fun getTask(id: String): Response<Task> {
        val token = sessionManager.getToken()
        return RetrofitTaskieInstance.api.getTask("Bearer $token", id)
    }

    override suspend fun updateTask(id: String, title: String, body: String): Response<Unit> {
        val token = sessionManager.getToken()
        return RetrofitTaskieInstance.api.updateTask("Bearer $token", id, CreateTaskRequest(title, body))
    }

    override suspend fun deleteTask(id: String): Response<Unit> {
        val token = sessionManager.getToken()
        return RetrofitTaskieInstance.api.deleteTask("Bearer $token", id)
    }

    override fun saveUsername(username: String) {
        sessionManager.saveUsername(username)
    }

    override fun getUsername(): String? {
        return sessionManager.getUsername()
    }

    override fun saveTaskDate(taskId: String, date: String) =
        sessionManager.saveTaskDate(taskId, date)

    override fun getTaskDate(taskId: String): String? =
        sessionManager.getTaskDate(taskId)
}