package com.example.taskie

import com.example.taskie.data.repository.TaskieRepository
import com.example.taskie.data.model.LoginRequest
import com.example.taskie.data.model.LoginResponse
import com.example.taskie.ui.LoginScreen.LoginViewModel
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

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel : LoginViewModel
    private lateinit var repository: TaskieRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun SuccessfulLoginSetsLoginSuccessToTrue() = runTest {

        val fakeResponse = Response.success(LoginResponse(token = "fake_token"))
        coEvery { repository.login(any()) } returns fakeResponse

        viewModel.username = "triki"
        viewModel.password = "password123"
        viewModel.login()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.loginSuccess)

    }
}