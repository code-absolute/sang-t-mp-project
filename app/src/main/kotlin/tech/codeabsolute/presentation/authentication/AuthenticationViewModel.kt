package tech.codeabsolute.presentation.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.codeabsolute.model.AuthResult
import tech.codeabsolute.use_cases.authenticate_user.AuthenticateQuickbooksUserUseCase
import tech.codeabsolute.use_cases.get_authentication_url.GetQuickbooksAuthenticationRequestUrlUseCase
import tech.codeabsolute.use_cases.set_access_tokens.SetQuickbooksAccessTokensUseCase
import java.net.URI

class AuthenticationViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val getQuickbooksAuthenticationRequestUrlUseCase: GetQuickbooksAuthenticationRequestUrlUseCase,
    private val authenticateQuickbooksUserUseCase: AuthenticateQuickbooksUserUseCase,
    private val setQuickbooksAccessTokensUseCase: SetQuickbooksAccessTokensUseCase
) {

    var uiState by mutableStateOf(AuthenticationViewState())
        private set

    init {
        startLoading()
    }

    fun authenticate() {
        CoroutineScope(ioDispatcher).launch {
            val authResult = authenticateQuickbooksUserUseCase()
            uiState = when (authResult) {
                is AuthResult.Authorized -> uiState.copy(isQuickbooksAuthenticated = true)
                is AuthResult.Error,
                is AuthResult.Unauthorized -> {
                    val url = getQuickbooksAuthenticationRequestUrlUseCase()
                    uiState.copy(
                        isQuickbooksAuthenticated = false,
                        url = url
                    )
                }
            }
            stopLoading()
        }
    }

    private fun startLoading() {
        uiState = uiState.copy(isLoading = true)
    }

    private fun stopLoading() {
        uiState = uiState.copy(isLoading = false)
    }

    fun onEvent(event: AuthenticationEvent) {
        when (event) {
            AuthenticationEvent.Login -> uiState = uiState.copy(isLoading = false, isAuthenticated = true)
            AuthenticationEvent.Logout -> uiState = uiState.copy(isLoading = false, isAuthenticated = false)
            is AuthenticationEvent.QuickbooksLogin -> setAccessTokens(event.redirectURI)
        }
    }

    private fun setAccessTokens(redirectURI: URI) {
        CoroutineScope(mainDispatcher).launch {
            startLoading()
            uiState = when (setQuickbooksAccessTokensUseCase(redirectURI)) {
                is AuthResult.Authorized -> uiState.copy(isQuickbooksAuthenticated = true)
                is AuthResult.Error,
                is AuthResult.Unauthorized -> uiState.copy(isQuickbooksAuthenticated = false)
            }
            stopLoading()
        }
    }
}