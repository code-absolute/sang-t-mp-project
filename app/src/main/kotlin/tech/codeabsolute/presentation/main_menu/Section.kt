package tech.codeabsolute.presentation.main_menu

sealed class Section {
    object Clients : Section()
    object NewClient : Section()
    data class ClientDetails(val clientId: Int?) : Section()
    object Reports : Section()
    object Settings : Section()
}