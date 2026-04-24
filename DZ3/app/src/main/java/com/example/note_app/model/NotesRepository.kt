package com.example.note_app.model

import androidx.compose.runtime.mutableStateListOf
import com.example.note_app.Note
import java.time.LocalDate


val notesList =
    mutableListOf<Note>(
        Note(1, "Note 1", "Hello this is my first note.", LocalDate.of(2026, 4, 7)),
        Note(2, "Note 2", "Hello this is my first note.", LocalDate.of(2026, 4, 7)),
        Note(3, "Note 3", "Hello this is my first note.", LocalDate.of(2026, 4, 7)),
        Note(4, "Note 4", "Hello this is my first note.", LocalDate.of(2026, 4, 7)),
        Note(5, "Note 5", "Hello this is my first note.", LocalDate.of(2026, 4, 7)),
        Note(6, "Note 6", "Hello this is my first note.", LocalDate.of(2026, 4, 7)),
        Note(7, "Note 7", "Hello this is my first note.", LocalDate.of(2026, 4, 7)),
        Note(8, "Note 8", "Hello this is my first note.", LocalDate.of(2026, 4, 7)),
        Note(9, "Note 9", "Hello this is my first note.", LocalDate.of(2026, 4, 7)),
        Note(10, "Note 10", "Hello this is my first note.", LocalDate.of(2026, 4, 7)),
    )


val notesRepository by lazy {
    NotesRepository(
        notesList = notesList
    )
}

class NotesRepository(
    private val notesList: MutableList<Note>
) {
    fun getNotes(): GetListResult {
        return try {
            GetListResult.Success(notesList)
        } catch (e: Exception) {
            GetListResult.Failure
        }
    }

    fun getNote(id: Int): Note? {
        val existingNote = notesList.find { it.id == id }
        return existingNote
    }

    fun addNote(title: String, description: String) {
        val newId =
            if (this.notesList.isEmpty()) 1
            else this.notesList.maxOf { it.id } + 1

        notesList.add(
            Note(newId, title, description, LocalDate.now())
        )
    }

    fun updateNote(id: Int, title: String, description: String) {
        val index = notesList.indexOfFirst { it.id == id }

        if(index == -1) return

        val updatedNote = notesList[index].copy(
            title = title,
            description = description
        )

        notesList[index] = updatedNote
    }


}


sealed interface GetListResult {
    data class Success(val list: List<Note>) : GetListResult
    data object Failure : GetListResult
}