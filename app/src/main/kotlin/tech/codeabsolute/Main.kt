package tech.codeabsolute

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.ksp.generated.QuickbooksKoinModuleModule
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module
import tech.codeabsolute.data.local.AppDatabase
import tech.codeabsolute.di.AppKoinModule
import tech.codeabsolute.di.QuickbooksKoinModule
import tech.codeabsolute.presentation.authentication.AuthenticationView
import tech.codeabsolute.ui.theme.BasicComposeTheme
import tech.codeabsolute.util.Resource
import tech.codeabsolute.util.empty

class Application : KoinComponent {

    fun start() = application {

        startKoin {
            printLogger()
            modules(
                defaultModule,
                AppKoinModule().module,
                QuickbooksKoinModule().module
            )
        }

        val viewModel by inject<AppViewModel>()
        val uiState = viewModel.uiState

        Window(
            title = "Sang-T MP",
            state = WindowState(placement = WindowPlacement.Maximized),
            onCloseRequest = ::exitApplication
        ) {
            BasicComposeTheme {
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                } else if (uiState.error.isNotEmpty()) {
                    Text("Error: ${uiState.error}")
                } else {
                    AuthenticationView(window)
                }
            }
        }
    }
}

fun main() {
    Application().start()
}

class AppViewModel(
    private val appDatabase: AppDatabase
) {

    var uiState by mutableStateOf(AppState())
        private set

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val resource = appDatabase.build()
            CoroutineScope(Dispatchers.Main).launch {
                uiState = when (resource) {
                    is Resource.Loading -> uiState.copy(isLoading = true)
                    is Resource.Success -> uiState.copy(isLoading = false)
                    is Resource.Error -> uiState.copy(isLoading = false, error = resource.message ?: String.empty())
                }
            }
        }
    }
}

data class AppState(
    val isLoading: Boolean = false,
    val error: String = String.empty()
)

