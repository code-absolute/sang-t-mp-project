package tech.codeabsolute.presentation.main_menu

data class MainMenuViewState(
    val menuItems: List<MenuItem> = listOf(),
    val formattedDate: String = "",
    val formattedTime: String = ""
)
