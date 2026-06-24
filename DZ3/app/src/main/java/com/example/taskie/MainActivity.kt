package com.example.taskie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskie.data.SessionManager
import com.example.taskie.ui.theme.Note_appTheme
import com.example.taskie.ui.EditScreen.EditTaskScreen
import com.example.taskie.ui.ListScreen.TasksListScreen
import com.example.taskie.ui.LoginScreen.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            Note_appTheme {
                val sessionManager = SessionManager(this)
                val startDestination = if (sessionManager.getToken() != null) "list" else "login"
                NavHost(navController = navController, startDestination = startDestination) {

                    composable("login") {
                        LoginScreen( onLoginClick = {
                            navController.navigate("list") {
                                popUpTo("login") {
                                    inclusive = true            // da kad kliknem nazad ne ode na login
                                }
                            }
                        })
                    }

                    composable("list") {
                        TasksListScreen(
                            onAddClick = { navController.navigate("detail/-1") },
                            onTaskClick = { taskId ->
                                navController.navigate("detail/$taskId") },

                        )


                    }

                    composable("detail/{taskId}") { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getString("taskId") ?: "-1"

                        EditTaskScreen(
                            taskId,
                            onBack = { navController.popBackStack() }
                        )
                    }

                }
            }
        }
    }
}

