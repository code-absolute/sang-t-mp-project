package tech.codeabsolute.presentation.login

sealed class LoginFormEvent {
    data class UsernameChanged(val username: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()
    data class Login(val onSuccessfulLogin: () -> Unit) : LoginFormEvent()
}
