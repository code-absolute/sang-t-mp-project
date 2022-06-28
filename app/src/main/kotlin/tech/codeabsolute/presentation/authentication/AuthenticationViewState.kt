package tech.codeabsolute.presentation.authentication

import tech.codeabsolute.model.Url
import tech.codeabsolute.util.empty

data class AuthenticationViewState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isQuickbooksAuthenticated: Boolean = false,
    val url: Url = Url(String.empty())
)
