package tech.codeabsolute.presentation.main_menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import compose.icons.FeatherIcons
import compose.icons.feathericons.FileText
import compose.icons.feathericons.Settings
import compose.icons.feathericons.UserPlus
import compose.icons.feathericons.Users
import kotlinx.coroutines.*
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

class MainMenuViewModel {

    var uiState by mutableStateOf(MainMenuViewState())
        private set

    init {
        createMenu()
        setDateAndTime()
    }

    private fun createMenu() {
        val menuItems = listOf(
            MenuItem(
                text = "Clients",
                active = true,
                icon = FeatherIcons.Users,
                section = Section.Clients,
                event = MainMenuEvent.OpenClientsSection
            ),
            MenuItem(
                text = "New Client",
                icon = FeatherIcons.UserPlus,
                section = Section.NewClient,
                event = MainMenuEvent.OpenNewClientSection
            ),
//            MenuItem(
//                text = "Reports",
//                icon = FeatherIcons.FileText,
//                section = Section.Reports,
//                event = MainMenuEvent.OpenReportsSection
//            ),
            MenuItem(
                text = "Settings",
                icon = FeatherIcons.Settings,
                section = Section.Settings,
                event = MainMenuEvent.OpenSettingsSection
            )
        )
        uiState = uiState.copy(menuItems = menuItems)
    }

    private fun setDateAndTime() {
        val dateFormatter = DateTimeFormat.fullDate()
        val timeFormatter = DateTimeFormat.fullTime()

        CoroutineScope(Dispatchers.Unconfined).launch {
            while (isActive) {
                val now = LocalDateTime.now()
                uiState = uiState.copy(
                    formattedDate = dateFormatter.print(now),
                    formattedTime = timeFormatter.print(now)
                )
                delay(1000)
            }
        }
    }

    fun onEvent(event: MainMenuEvent, onSectionChange: ((Section) -> Unit) = {}) {
        when (event) {
            MainMenuEvent.OpenClientsSection,
            MainMenuEvent.OpenNewClientSection,
            MainMenuEvent.OpenReportsSection,
            MainMenuEvent.OpenSettingsSection -> changeSection(event, onSectionChange)
            is MainMenuEvent.Logout -> logout(event)
        }
    }

    private fun changeSection(event: MainMenuEvent, onSectionChange: (Section) -> Unit) {
        uiState = updateActiveMenu(event.section)
        event.section?.let(onSectionChange)
    }

    private fun updateActiveMenu(
        section: Section?
    ): MainMenuViewState {
        return uiState.copy(menuItems = uiState.menuItems.map {
            it.copy(active = (it.section == section))
        })
    }

    private fun logout(event: MainMenuEvent.Logout) {
        event.onLogout()
        createMenu()
    }
}