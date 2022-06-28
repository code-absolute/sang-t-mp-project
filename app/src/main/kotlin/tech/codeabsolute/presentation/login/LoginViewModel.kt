package tech.codeabsolute.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import tech.codeabsolute.model.LoginCredentials
import tech.codeabsolute.use_cases.authenticate_local_user.AuthenticateLocalUserUseCase
import tech.codeabsolute.util.empty

@Single
class LoginViewModel(
    private val authenticateLocalUserUseCase: AuthenticateLocalUserUseCase,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    var uiState by mutableStateOf(LoginViewState())

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.UsernameChanged -> {
                uiState = uiState.copy(username = event.username)
            }
            is LoginFormEvent.PasswordChanged -> {
                uiState = uiState.copy(password = event.password)
            }
            is LoginFormEvent.Login -> login(event.onSuccessfulLogin)
        }
    }

    private fun login(onSuccessfulLogin: () -> Unit) {
        CoroutineScope(mainDispatcher).launch {
            val account = authenticateLocalUserUseCase(LoginCredentials(uiState.username, uiState.password))
            if (account == null) {
                uiState = uiState.copy(loginError = "Invalid login")
            } else {
                uiState = uiState.copy(
                    username = String.empty(),
                    password = String.empty(),
                    loginError = null
                )
                onSuccessfulLogin()
            }
        }
    }
}