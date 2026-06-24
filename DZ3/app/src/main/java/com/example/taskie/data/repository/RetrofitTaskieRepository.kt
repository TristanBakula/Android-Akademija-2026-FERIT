package com.example.taskie.data.repository

import android.util.Log.i
import com.example.taskie.data.SessionManager
import com.example.taskie.data.model.CreateTaskRequest
import com.example.taskie.data.model.CreateTaskResponse
import com.example.taskie.data.model.LoginRequest
import com.example.taskie.data.model.LoginResponse
import com.example.taskie.data.model.Task
import com.example.taskie.data.model.TaskResponse
import com.example.taskie.data.network.RetrofitTaskieInstance
import com.example.taskie.data.database.TaskDao
import com.example.taskie.data.database.entities.TaskEntity
import com.example.taskie.data.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.Response

class RetrofitTaskieRepository(
    private val sessionManager: SessionManager,
    private val taskDao: TaskDao
) : TaskieRepository {

    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return RetrofitTaskieInstance.api.login(request)
    }

    override fun saveToken(token: String) = sessionManager.saveToken(token)

    override suspend fun getTasks(): Response<TaskResponse> {

        val token = sessionManager.getToken()
        val response = RetrofitTaskieInstance.api.getTasks("Bearer $token")

        if(response.isSuccessful) {

            val apiTasks = response.body()?.tasks ?: emptyList()
            val entities = apiTasks.map { it.toEntity() }
            taskDao.insertTasks(entities)
        }

        return response
    }

    /*private suspend fun syncPendingTasks() {
        val allTasks = taskDao.getAllTasks().first()
        val unsynced = allTasks.filter { !it.isSynced }

        for (task in unsynced) {
            try {
                val token = sessionManager.getToken() ?: continue
                val response = RetrofitTaskieInstance.api.createTask(
                    "Bearer $token",
                    CreateTaskRequest(task.title ?: "", task.body ?: "")
                )
                if (response.isSuccessful) {
                    val serverId = response.body()?.id?.toString() ?: continue
                    taskDao.deleteTaskById(task.id)
                    taskDao.insertTask(task.copy(id = serverId, isSynced = true))
                }
            } catch (e: Exception) {

            }
        }
    } */

    override fun getTasksFlow(): Flow<List<TaskEntity>> {
        return taskDao.getAllTasks()
    }


    override suspend fun createTask(title: String, body: String): Response<CreateTaskResponse> {
        val tempId = System.currentTimeMillis().toString()
        val localTask = TaskEntity(
            id = tempId,
            title = title,
            body = body,
            isSynced = false
        )
        taskDao.insertTask(localTask)
        return try {
            val token = sessionManager.getToken()
            val response = RetrofitTaskieInstance.api.createTask("Bearer $token",CreateTaskRequest(title, body))

            if(response.isSuccessful) {
                val serverId = response.body()?.id?.toString() ?: ""
                taskDao.deleteTaskById(localTask.id)
                taskDao.insertTask(localTask.copy(id = serverId, isSynced = true))
            }
            response
        } catch (e: Exception) {
            Response.error(500, okhttp3.ResponseBody.create(null, ""))
        }


    }

    override suspend fun getTask(id: String): Response<Task> {
        val token = sessionManager.getToken()
        return RetrofitTaskieInstance.api.getTask("Bearer $token", id)
    }

    override suspend fun getTaskByIdLocal(id: String): TaskEntity? {
        return taskDao.getTaskById(id)
    }

    override suspend fun updateTask(id: String, title: String, body: String): Response<Unit> {
        val updatedLocalTask = TaskEntity(
            id = id,
            title = title,
            body = body,
            isSynced = false
        )

        taskDao.updateTask(updatedLocalTask)

        return try {
            val token = sessionManager.getToken()

            val response = RetrofitTaskieInstance.api.updateTask(
                "Bearer $token",
                id,
                CreateTaskRequest(title, body)
            )

            if (response.isSuccessful) {
                taskDao.updateTask(updatedLocalTask.copy(isSynced = true))
            }
            response
        } catch (e: Exception) {
            Response.error(500, okhttp3.ResponseBody.create(null, ""))
        }
    }

    override suspend fun deleteTask(id: String): Response<Unit> {
        taskDao.deleteTaskById(id)

        return try {
            val token = sessionManager.getToken()
            RetrofitTaskieInstance.api.deleteTask("Bearer $token", id)
        } catch (e: Exception) {
            Response.error(500, okhttp3.ResponseBody.create(null, ""))
        }
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