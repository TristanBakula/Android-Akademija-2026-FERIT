package com.example.taskie

import com.example.taskie.data.repository.TaskieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import com.example.taskie.data.model.CreateTaskResponse
import com.example.taskie.ui.EditScreen.EditTaskScreenViewModel

@OptIn
class EditTaskScreenViewModelTest {

    private lateinit var viewModel: EditTaskScreenViewModel
    private lateinit var repository: TaskieRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        viewModel = EditTaskScreenViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun SaveTaskSetsIsSavedToTrue() = runTest {
        val fakeResponse = Response.success(CreateTaskResponse(id = "123"))
        coEvery { repository.createTask(any(), any()) } returns fakeResponse


        viewModel.initTask("-1")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.titleText = "Test Title"
        viewModel.bodyText = "Test Body"
        viewModel.saveTask()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.isSaved)
    }
}