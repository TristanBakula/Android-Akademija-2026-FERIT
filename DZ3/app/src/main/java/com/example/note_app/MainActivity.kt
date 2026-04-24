package com.example.note_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.note_app.model.notesList
import com.example.note_app.presentation.EditScreenViewModel
import com.example.note_app.presentation.ListScreenViewModel
import com.example.note_app.presentation.NotesListUiState
import com.example.note_app.ui.theme.Note_appTheme
import com.example.note_app.ui.DetailScreen
import com.example.note_app.ui.ErrorScreen
import com.example.note_app.ui.LoadingScreen
import com.example.note_app.ui.NotesListScreen
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            Note_appTheme {
                NavHost(navController = navController, startDestination = "list") {

                    composable("list") {
                        val viewModel: ListScreenViewModel = viewModel(factory = ListScreenViewModel.Factory)
                        LaunchedEffect(Unit) { viewModel.getList() }

                        when(val state = viewModel.uiState.collectAsStateWithLifecycle().value) {
                            is NotesListUiState.Loaded -> NotesListScreen(
                                notes = state.list,
                                onAddClick = { navController.navigate("detail/-1") },
                                onNoteClick = { noteId -> navController.navigate("detail/$noteId") }
                            )
                            is NotesListUiState.Failure -> ErrorScreen(
                                messageResId = state.messageResId,
                                onRetryClick = viewModel::getList
                            )
                            NotesListUiState.Loading -> LoadingScreen()
                        }


                    }

                    composable("detail/{noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId")?.toInt() ?: -1

                        val viewModel: EditScreenViewModel = viewModel(factory = EditScreenViewModel.Factory)

                        LaunchedEffect(Unit) { viewModel.initNote(noteId) }
                        DetailScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }

                }
            }
        }
    }
}

