package tech.codeabsolute.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.java.KoinJavaComponent
import tech.codeabsolute.presentation.client_details.ClientDetailsSection
import tech.codeabsolute.presentation.clients.ClientsSection
import tech.codeabsolute.presentation.main_menu.MainMenu
import tech.codeabsolute.presentation.main_menu.Section
import tech.codeabsolute.presentation.new_client.NewClientSection
import tech.codeabsolute.presentation.settings.SettingsSection

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = KoinJavaComponent.get(HomeViewModel::class.java),
    onLogout: () -> Unit
) {

    val uiState = viewModel.uiState

    Row(modifier = Modifier.fillMaxWidth()) {
        MainMenu(
            onSectionChange = { viewModel.onEvent(HomeScreenEvent.ChangeSection(it)) },
            onLogout = onLogout
        )
        Column(modifier = Modifier.padding(48.dp)) {
            when (uiState.section) {
                Section.Clients -> ClientsSection {
                    viewModel.onEvent(HomeScreenEvent.ChangeSection(Section.ClientDetails(it)))
                }
                is Section.ClientDetails -> ClientDetailsSection(uiState.section.clientId)
                Section.NewClient -> NewClientSection {
                    viewModel.onEvent(HomeScreenEvent.ChangeSection(Section.ClientDetails(it)))
                }
                Section.Reports -> {}
                Section.Settings -> SettingsSection()
            }
        }
    }
}