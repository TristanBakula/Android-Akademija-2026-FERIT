package com.example.taskie.data

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) = prefs.edit().putString("auth_token", token).apply()
    fun getToken() = prefs.getString("auth_token", null)

    fun saveUsername(username: String) = prefs.edit().putString("username", username).apply()

    fun getUsername() = prefs.getString("username", null)

    fun saveTaskDate(taskId: String, date: String) =
        prefs.edit().putString("task_date_$taskId", date).apply()

    fun getTaskDate(taskId: String): String? =
        prefs.getString("task_date_$taskId", null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
