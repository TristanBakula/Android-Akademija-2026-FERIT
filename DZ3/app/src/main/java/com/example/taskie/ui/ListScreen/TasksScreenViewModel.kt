package com.example.note_app.ui.ListScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.note_app.R
import com.example.note_app.data.model.Task
import com.example.note_app.data.repository.RetrofitTaskieRepository
import com.example.note_app.data.repository.TaskieRepository
import androidx.compose.runtime.State
import kotlinx.coroutines.launch

class TasksScreenViewModel(private val repository: TaskieRepository) : ViewModel() {

    private val _tasksListUIState: MutableState<TasksListUiState> = mutableStateOf(
        TasksListUiState.Loading
    )

    val tasksListUIState: State<TasksListUiState> = _tasksListUIState

    val currentUsername: String? = repository.getUsername()

    fun getTaskDate(taskId: String): String? = repository.getTaskDate(taskId)

    fun getTasks() {
        viewModelScope.launch {
            _tasksListUIState.value = TasksListUiState.Loading

            try {
                val response = repository.getTasks()

                if (response.isSuccessful) {
                    val taskList = response.body()?.tasks ?: emptyList()
                    _tasksListUIState.value = TasksListUiState.Loaded(list = taskList)
                } else {
                    _tasksListUIState.value = TasksListUiState.Failure(R.string.error_load_tasks)
                }
            } catch (e: Exception) {
                android.util.Log.e("TasksError", "Greška pri dohvatu: ${e.message}", e)
                _tasksListUIState.value = TasksListUiState.Failure(R.string.error_no_internet)
            }
        }
    }


    fun deleteTask(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.deleteTask(id)
                if (response.isSuccessful) {
                    getTasks()
                }
            } catch (e: Exception) {
                android.util.Log.e("TasksError", "Greška pri brisanju: ${e.message}", e)
            }
        }
    }

    companion object {
        fun provideFactory(context: android.content.Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val sessionManager = com.example.note_app.data.SessionManager(context)
                    val repository = RetrofitTaskieRepository(sessionManager)
                    return TasksScreenViewModel(repository) as T
                }
            }
    }
}

sealed interface TasksListUiState {
    data class Loaded(val list: List<Task>) : TasksListUiState
    data object Loading : TasksListUiState
    data class Failure(val messageResId: Int) : TasksListUiState
}