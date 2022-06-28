package tech.codeabsolute.presentation.authentication

import java.net.URI

sealed class AuthenticationEvent {
    object Login : AuthenticationEvent()
    object Logout : AuthenticationEvent()
    data class QuickbooksLogin(val redirectURI: URI) : AuthenticationEvent()
}
