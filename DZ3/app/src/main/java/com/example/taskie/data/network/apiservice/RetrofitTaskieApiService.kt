package com.example.taskie.data.network.apiservice

import com.example.taskie.data.model.CreateTaskRequest
import com.example.taskie.data.model.CreateTaskResponse
import com.example.taskie.data.model.LoginRequest
import com.example.taskie.data.model.LoginResponse
import com.example.taskie.data.model.Task
import com.example.taskie.data.model.TaskResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RetrofitTaskieApiService {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("tasks/all")
    suspend fun getTasks(
        @Header("Authorization") token: String
    ): Response<TaskResponse>

    @POST("tasks/create")
    suspend fun createTask(
        @Header("Authorization") token: String,
        @Body request: CreateTaskRequest
    ): Response<CreateTaskResponse>

    @GET("tasks/{id}")
    suspend fun getTask(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Task>

    @PUT("tasks/{id}")
    suspend fun updateTask(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: CreateTaskRequest
    ): Response<Unit>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Unit>
}