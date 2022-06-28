package tech.codeabsolute.presentation.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import org.koin.java.KoinJavaComponent
import tech.codeabsolute.presentation.home.HomeScreen
import tech.codeabsolute.presentation.login.LoginScreen

@Composable
fun AuthenticationView(
    composeWindow: ComposeWindow,
    viewModel: AuthenticationViewModel = KoinJavaComponent.get(AuthenticationViewModel::class.java)
) {

    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.authenticate()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            if (uiState.isAuthenticated) {
                HomeScreen { viewModel.onEvent(AuthenticationEvent.Logout) }
            } else if (uiState.isQuickbooksAuthenticated) {
                LoginScreen { viewModel.onEvent(AuthenticationEvent.Login) }
            } else {
                QuickbooksAuthenticationView(composeWindow, uiState.url) { redirectURI ->
                    viewModel.onEvent(AuthenticationEvent.QuickbooksLogin(redirectURI))
                }
            }
        }
    }
}
//code=AB11656381118s6SRD536b2irqbdtE8dCsyNkeSgHhsN8Nz0tX&state=7b2b6603-0dda-499f-88af-7fa932b99caf&realmId=4620816365227933210