package com.example.note_app.ui.LoginScreen

import android.util.Log.e
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.note_app.data.model.LoginRequest
import com.example.note_app.data.repository.RetrofitTaskieRepository
import com.example.note_app.data.repository.TaskieRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: TaskieRepository
) : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf("")
    var loginSuccess by mutableStateOf(false)


    fun login() {
        viewModelScope.launch {
            try {
                val result = repository.login(
                    LoginRequest(username, password)
                )
                if (result.isSuccessful) {
                    val token = result.body()?.token
                    if(token != null){
                        repository.saveToken(token)
                        repository.saveUsername(username)
                        loginSuccess = true
                    }
                    else
                        errorMessage = "Wrong response from server!"
                }
                else {
                    errorMessage = "Wrong credentials!"
                }
            } catch (e: Exception) {
                errorMessage = "Internet error: ${e.message}"
            }
        }
            }

    companion object {
        fun provideFactory(context: android.content.Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val sessionManager = com.example.note_app.data.SessionManager(context)
                val repository = RetrofitTaskieRepository(sessionManager)
                return LoginViewModel(repository) as T
            }
        }
    }
}