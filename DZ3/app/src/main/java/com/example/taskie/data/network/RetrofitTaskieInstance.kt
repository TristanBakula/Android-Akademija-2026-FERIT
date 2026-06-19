package com.example.note_app.data.network

import com.example.note_app.data.network.apiservice.RetrofitTaskieApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

// Konfiguracija JSON-a da ignorira polja koja mu nisu bitna
private val jsonConfig = Json {
    ignoreUnknownKeys = true
}

object RetrofitTaskieInstance {
    private const val BASE_URL = "https://ada-taskie-backend.osc-fr1.scalingo.io/"
    private val contentType = "application/json".toMediaType()

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: RetrofitTaskieApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(jsonConfig.asConverterFactory(contentType))
            .build()
            .create(RetrofitTaskieApiService::class.java)
    }
}