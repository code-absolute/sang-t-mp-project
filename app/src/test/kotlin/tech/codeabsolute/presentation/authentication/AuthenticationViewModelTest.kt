package tech.codeabsolute.presentation.authentication

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.codeabsolute.model.AuthResult
import tech.codeabsolute.model.Url
import tech.codeabsolute.use_cases.authenticate_user.AuthenticateQuickbooksUserUseCase
import tech.codeabsolute.use_cases.get_authentication_url.GetQuickbooksAuthenticationRequestUrlUseCase
import tech.codeabsolute.use_cases.set_access_tokens.SetQuickbooksAccessTokensUseCase
import java.net.URI

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class AuthenticationViewModelTest {

    @MockK
    private lateinit var getQuickbooksAuthenticationRequestUrlUseCase: GetQuickbooksAuthenticationRequestUrlUseCase

    @MockK
    private lateinit var authenticateQuickbooksUserUseCase: AuthenticateQuickbooksUserUseCase

    @MockK
    private lateinit var setQuickbooksAccessTokensUseCase: SetQuickbooksAccessTokensUseCase

    private lateinit var viewModel: AuthenticationViewModel

    @Test
    fun `Initial UI state`() {

        val expectedUIState = AuthenticationViewState(isLoading = true)

        initViewModel()

        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    private fun initViewModel(
        ioDispatcher: CoroutineDispatcher = StandardTestDispatcher(),
        mainDispatcher: CoroutineDispatcher = StandardTestDispatcher()
    ) {
        viewModel = AuthenticationViewModel(
            ioDispatcher,
            mainDispatcher,
            getQuickbooksAuthenticationRequestUrlUseCase,
            authenticateQuickbooksUserUseCase,
            setQuickbooksAccessTokensUseCase
        )
    }

    @Test
    fun `Authenticate Quickbooks user returns AuthResult Authorized`() = runTest {

        val expectedUIState = AuthenticationViewState(isLoading = false, isQuickbooksAuthenticated = true)
        val ioDispatcher = StandardTestDispatcher(testScheduler)
        val mainDispatcher = StandardTestDispatcher(testScheduler)
        val authResult = mockk<AuthResult.Authorized<Unit>>()
        coEvery { authenticateQuickbooksUserUseCase() } returns authResult
        initViewModel(ioDispatcher = ioDispatcher, mainDispatcher = mainDispatcher)
        advanceUntilIdle()

        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    @Test
    fun `Authenticate Quickbooks user returns AuthResult Unauthorized`() = runTest {

        val url = mockk<Url>()
        val expectedUIState = AuthenticationViewState(
            isLoading = false,
            isQuickbooksAuthenticated = false,
            url = url
        )
        val ioDispatcher = StandardTestDispatcher(testScheduler)
        val mainDispatcher = StandardTestDispatcher(testScheduler)
        val authResult = mockk<AuthResult.Unauthorized<Unit>>()
        coEvery { authenticateQuickbooksUserUseCase() } returns authResult
        coEvery { getQuickbooksAuthenticationRequestUrlUseCase() } returns url
        initViewModel(ioDispatcher = ioDispatcher, mainDispatcher = mainDispatcher)
        advanceUntilIdle()

        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    @Test
    fun `Authenticate Quickbooks user returns AuthResult Error`() = runTest {

        val url = mockk<Url>()
        val expectedUIState = AuthenticationViewState(
            isLoading = false,
            isQuickbooksAuthenticated = false,
            url = url
        )
        val ioDispatcher = StandardTestDispatcher(testScheduler)
        val mainDispatcher = StandardTestDispatcher(testScheduler)
        val authResult = mockk<AuthResult.Error<Unit>>()
        coEvery { authenticateQuickbooksUserUseCase() } returns authResult
        coEvery { getQuickbooksAuthenticationRequestUrlUseCase() } returns url
        initViewModel(ioDispatcher = ioDispatcher, mainDispatcher = mainDispatcher)
        advanceUntilIdle()

        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    @Test
    fun `Set access tokens starts loading`() {

        val uri = mockk<URI>()
        val expectedUIState = AuthenticationViewState(isLoading = true)
        initViewModel()

        viewModel.onEvent(AuthenticationEvent.QuickbooksLogin(uri))
        val uiState = viewModel.uiState

        assertEquals(uiState, expectedUIState)
    }

    @Test
    fun `Set access tokens returns AuthResult Authorized`() = runTest {

        val uri = mockk<URI>()
        val expectedUIState = AuthenticationViewState(
            isLoading = false,
            isQuickbooksAuthenticated = true
        )
        val mainDispatcher = StandardTestDispatcher(testScheduler)
        val authResult = mockk<AuthResult.Authorized<Unit>>()
        coEvery { setQuickbooksAccessTokensUseCase(uri) } returns authResult
        initViewModel(mainDispatcher = mainDispatcher)

        viewModel.onEvent(AuthenticationEvent.QuickbooksLogin(uri))
        advanceUntilIdle()
        val uiState = viewModel.uiState

        assertEquals(uiState, expectedUIState)
    }

    @Test
    fun `Set access tokens returns AuthResult Error`() = runTest {

        val uri = mockk<URI>()
        val expectedUIState = AuthenticationViewState(
            isLoading = false,
            isQuickbooksAuthenticated = false
        )
        val mainDispatcher = StandardTestDispatcher(testScheduler)
        val authResult = mockk<AuthResult.Error<Unit>>()
        coEvery { setQuickbooksAccessTokensUseCase(uri) } returns authResult
        initViewModel(mainDispatcher = mainDispatcher)

        viewModel.onEvent(AuthenticationEvent.QuickbooksLogin(uri))
        advanceUntilIdle()
        val uiState = viewModel.uiState

        assertEquals(uiState, expectedUIState)
    }

    @Test
    fun `Set access tokens returns AuthResult Unauthorized`() = runTest {

        val uri = mockk<URI>()
        val expectedUIState = AuthenticationViewState(
            isLoading = false,
            isQuickbooksAuthenticated = false
        )
        val mainDispatcher = StandardTestDispatcher(testScheduler)
        val authResult = mockk<AuthResult.Unauthorized<Unit>>()
        coEvery { setQuickbooksAccessTokensUseCase(uri) } returns authResult
        initViewModel(mainDispatcher = mainDispatcher)

        viewModel.onEvent(AuthenticationEvent.QuickbooksLogin(uri))
        advanceUntilIdle()
        val uiState = viewModel.uiState

        assertEquals(uiState, expectedUIState)
    }

    @Test
    fun `Authentication Event Login`() {

        val expectedUIState = AuthenticationViewState(isAuthenticated = true)
        initViewModel()

        viewModel.onEvent(AuthenticationEvent.Login)
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }

    @Test
    fun `Authentication Event Logout`() {

        val expectedUIState = AuthenticationViewState(isAuthenticated = false)
        initViewModel()

        viewModel.onEvent(AuthenticationEvent.Logout)
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
    }
}