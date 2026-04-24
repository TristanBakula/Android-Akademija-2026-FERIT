package com.example.note_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.note_app.Note
import com.example.note_app.model.GetListResult
import com.example.note_app.model.NotesRepository
import com.example.note_app.model.notesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.ui.res.stringResource
import com.example.note_app.R


class ListScreenViewModel(private val repository: NotesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<NotesListUiState>(NotesListUiState.Loading)

    val uiState = _uiState.asStateFlow()

    fun getList() {
        _uiState.value = NotesListUiState.Loading

        val result = repository.getNotes()

        _uiState.value = when (result) {
            is GetListResult.Success ->
                NotesListUiState.Loaded(list = result.list)

            GetListResult.Failure ->
                NotesListUiState.Failure(R.string.error_load_notes)
        }

    }
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListScreenViewModel(notesRepository) as T
            }
        }
    }
}





sealed interface NotesListUiState {
    data class Loaded(
        val list: List<Note>
    ) : NotesListUiState
    data object Loading : NotesListUiState
    data class Failure(val messageResId: Int) : NotesListUiState
}
