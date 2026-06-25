package com.example.taskie.ui.ListScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskie.R
import com.example.taskie.data.model.Task
import com.example.taskie.data.repository.RetrofitTaskieRepository
import com.example.taskie.data.repository.TaskieRepository
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.taskie.data.database.entities.toTask
import kotlinx.coroutines.NonCancellable.isCompleted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class TasksScreenViewModel(private val repository: TaskieRepository) : ViewModel() {

    private val _tasksListUIState: MutableState<TasksListUiState> = mutableStateOf(
        TasksListUiState.Loading
    )

    val tasksListUIState: State<TasksListUiState> = _tasksListUIState

    val currentUsername: String? = repository.getUsername()

    private val _currentFilter = MutableStateFlow(FilterType.ALL)
    val currentFilter: StateFlow<FilterType> = _currentFilter

    fun toggleTaskCompleted(taskId: String, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateTaskCompleted(taskId, isCompleted)
        }
    }

    fun setFilter(filter: FilterType) {
        _currentFilter.value = filter
    }

    fun getTaskDate(taskId: String): String? = repository.getTaskDate(taskId)

    init {
        viewModelScope.launch {
            repository.getTasksFlow()
                .combine(_currentFilter) { entities, filter ->
                    val tasks = entities.map { it.toTask() }
                    when (filter) {
                        FilterType.ALL -> tasks
                        FilterType.COMPLETED -> tasks.filter { it.isCompleted }
                        FilterType.DOING -> tasks.filter { !it.isCompleted }
                    }
            }
                .collect { filteredTasks ->
                    _tasksListUIState.value = TasksListUiState.Loaded(filteredTasks)
                }
        }

        getTasks()
    }

    fun getTasks() {
        viewModelScope.launch {

            try {
                repository.getTasks()

            } catch (e: Exception) {
                if (_tasksListUIState.value is TasksListUiState.Loading) {
                    _tasksListUIState.value = TasksListUiState.Failure(R.string.error_no_internet)
                }
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
                android.util.Log.e("TasksError", "Error while deleting: ${e.message}", e)
            }
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            repository.logout()
            onLoggedOut()
        }
    }

}

sealed interface TasksListUiState {
    data class Loaded(val list: List<Task>) : TasksListUiState
    data object Loading : TasksListUiState
    data class Failure(val messageResId: Int) : TasksListUiState
}

enum class FilterType { ALL, DOING, COMPLETED }