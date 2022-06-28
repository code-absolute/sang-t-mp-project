package tech.codeabsolute.presentation.login

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.codeabsolute.model.LoginCredentials
import tech.codeabsolute.use_cases.authenticate_local_user.AuthenticateLocalUserUseCase
import tech.codeabsolute.util.empty
import tech.codeabsolute.util.random

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class LoginViewModelTest {

    @MockK
    private lateinit var authenticateLocalUserUseCase: AuthenticateLocalUserUseCase

    private lateinit var mainDispatcher: CoroutineDispatcher
    private lateinit var viewModel: LoginViewModel

    @BeforeEach
    fun setUp() {
        mainDispatcher = StandardTestDispatcher()
        viewModel = LoginViewModel(authenticateLocalUserUseCase, mainDispatcher)
    }

    @Test
    fun `UsernameChanged event`() {

        val username = String.random()
        val expectedUIState = LoginViewState(username = username)

        viewModel.onEvent(LoginFormEvent.UsernameChanged(username))
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    @Test
    fun `PasswordChanged event`() {

        val password = String.random()
        val expectedUIState = LoginViewState(password = password)

        viewModel.onEvent(LoginFormEvent.PasswordChanged(password))
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    @Test
    fun `Login event with valid credentials`() = runTest(mainDispatcher) {

        val expectedUIState = LoginViewState(username = String.empty(), password = String.empty(), null)
        val initialUIState = LoginViewState(username = String.random(), password = String.random())
        viewModel.uiState = initialUIState
        val onSuccessfulLogin = mockk<() -> Unit>()
        coEvery {
            authenticateLocalUserUseCase(
                LoginCredentials(
                    initialUIState.username,
                    initialUIState.password
                )
            )
        } returns mockk()
        justRun { onSuccessfulLogin() }

        viewModel.onEvent(LoginFormEvent.Login(onSuccessfulLogin))
        advanceUntilIdle()
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
        verify { onSuccessfulLogin() }
    }

    @Test
    fun `Login event with invalid credentials`() = runTest(mainDispatcher) {

        val username = String.random()
        val password = String.random()
        val expectedUIState = LoginViewState(
            username = username,
            password = password,
            loginError = "Invalid login"
        )
        viewModel.uiState = LoginViewState(username, password)
        coEvery {
            authenticateLocalUserUseCase(
                LoginCredentials(
                    expectedUIState.username,
                    expectedUIState.password
                )
            )
        } returns null

        viewModel.onEvent(LoginFormEvent.Login {})
        advanceUntilIdle()
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }
}