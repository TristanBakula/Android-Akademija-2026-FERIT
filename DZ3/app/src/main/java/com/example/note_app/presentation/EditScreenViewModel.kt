package com.example.note_app.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.note_app.Note
import com.example.note_app.model.GetListResult
import com.example.note_app.model.NotesRepository
import com.example.note_app.model.notesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class EditScreenViewModel(private val repository: NotesRepository) : ViewModel() {

    var titleText by mutableStateOf("")
    var descriptionText by mutableStateOf("")

    private var currentNodeId: Int? = null

    fun initNote(id: Int) {
        if(id == -1) return
        else{
            val result = repository.getNote(id)
            if(result != null) {
                this.titleText = result.title
                this.descriptionText = result.description
                this.currentNodeId = result.id
            }
        }
    }

    fun saveNote() {
        val id = currentNodeId
        if (id == null) {
            repository.addNote(titleText, descriptionText)
        }
        else {
            repository.updateNote(id, titleText, descriptionText)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EditScreenViewModel(notesRepository) as T
            }
        }
    }
}