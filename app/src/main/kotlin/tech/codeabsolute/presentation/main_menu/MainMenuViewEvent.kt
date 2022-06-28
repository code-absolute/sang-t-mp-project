package tech.codeabsolute.presentation.main_menu

sealed class MainMenuEvent(val section: Section? = null) {

    object OpenClientsSection : MainMenuEvent(Section.Clients)
    object OpenNewClientSection : MainMenuEvent(Section.NewClient)
    object OpenReportsSection : MainMenuEvent(Section.Reports)
    object OpenSettingsSection : MainMenuEvent(Section.Settings)
    data class Logout(val onLogout: () -> Unit) : MainMenuEvent()
}
