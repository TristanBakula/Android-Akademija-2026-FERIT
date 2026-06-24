package com.example.taskie.di

import com.example.taskie.data.SessionManager
import com.example.taskie.data.database.TaskieDatabase
import com.example.taskie.data.network.RetrofitTaskieInstance
import com.example.taskie.data.repository.RetrofitTaskieRepository
import com.example.taskie.data.repository.TaskieRepository
import com.example.taskie.ui.EditScreen.EditTaskScreenViewModel
import com.example.taskie.ui.ListScreen.TasksScreenViewModel
import com.example.taskie.ui.LoginScreen.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { SessionManager(androidContext()) }

    single { TaskieDatabase.getDatabase(androidContext()) }
    single { get<TaskieDatabase>().taskDao() }

    single<TaskieRepository> { RetrofitTaskieRepository(get(), get()) }

    viewModel { LoginViewModel(get()) }
    viewModel { TasksScreenViewModel(get()) }
    viewModel { EditTaskScreenViewModel(get()) }
}
