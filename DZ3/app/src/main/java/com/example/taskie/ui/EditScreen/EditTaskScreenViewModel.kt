package com.example.taskie.ui.EditScreen

import android.service.notification.Condition.newId
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskie.data.repository.RetrofitTaskieRepository
import com.example.taskie.ui.ListScreen.ErrorScreen
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.taskie.data.model.Task
import com.example.taskie.data.repository.TaskieRepository

class EditTaskScreenViewModel(private val repository: TaskieRepository) : ViewModel() {

    var titleText by mutableStateOf("")
    var bodyText by mutableStateOf("")

    var isSaved by mutableStateOf(false) // ako je true vraca ga natrag kad spremi

    private var currentTaskId: String? = null

    var errorMessage by mutableStateOf("")

    fun initTask(id: String) {
        currentTaskId = id
        if(id == "-1") return

        viewModelScope.launch {
            try {
                val localTask= repository.getTaskByIdLocal(id)

                if(localTask!= null) {
                    titleText = localTask.title ?: ""
                    bodyText = localTask.body ?: ""
                } else {
                    val response = repository.getTask(id)
                    if (response.isSuccessful) {
                        val task = response.body()
                        titleText = task?.title ?: ""
                        bodyText = task?.body ?: ""
                    }
                }
            } catch (e: Exception) {
                errorMessage = "Task failed to load"
            }
        }
    }

    fun saveTask() {
        viewModelScope.launch {
            try {
                if(currentTaskId == "-1") {

                    repository.createTask(titleText, bodyText)
                    isSaved = true

                } else {
                    val id = currentTaskId ?: return@launch
                    val response = repository.updateTask(id, titleText, bodyText)
                    if(response.isSuccessful) {
                        isSaved = true
                    }
                }
            } catch (e: Exception) {
                errorMessage = "Task failed to save"
            }

        }
    }
}