package tech.codeabsolute.presentation.login

import tech.codeabsolute.util.empty

data class LoginViewState(
    val username: String = String.empty(),
    val password: String = String.empty(),
    val loginError: String? = null
)
