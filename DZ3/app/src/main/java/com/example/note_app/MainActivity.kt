package com.example.note_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.note_app.ui.theme.Note_appTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            val notesList = remember {
                mutableStateListOf<Note>(
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
            }

            Note_appTheme {
                NavHost(navController = navController, startDestination = "list") {

                    composable("list") {
                        NotesListScreen(
                            notes = notesList,
                            onAddClick = { navController.navigate("detail/-1") },
                            onNoteClick = { noteId -> navController.navigate("detail/$noteId") }
                        )
                    }

                    composable("detail/{noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId")?.toInt() ?: -1
                        val existingNote = notesList.find { it.id == noteId }

                        DetailScreen(
                            note = existingNote,
                            onBack = { navController.popBackStack() },
                            onSave = { title, desc ->
                                if(existingNote != null) {
                                    val index = notesList.indexOf(existingNote)
                                    notesList[index] = existingNote.copy(title = title, description = desc)
                                }
                                else {
                                    val newId = if (notesList.isEmpty()) 1 else notesList.maxOf { it.id } + 1
                                    notesList.add(Note(newId, title, desc, LocalDate.now()))
                                }
                                navController.popBackStack()
                            }
                        )
                    }

                }
            }
        }
    }
}

