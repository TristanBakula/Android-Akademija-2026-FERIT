package com.example.taskie.ui.LoginScreen

import android.util.Log.e
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskie.data.model.LoginRequest
import com.example.taskie.data.repository.RetrofitTaskieRepository
import com.example.taskie.data.repository.TaskieRepository
import com.example.taskie.data.database.TaskieDatabase
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

}