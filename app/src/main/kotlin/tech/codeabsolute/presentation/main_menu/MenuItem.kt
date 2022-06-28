package tech.codeabsolute.presentation.main_menu

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val text: String,
    val active: Boolean = false,
    val icon: ImageVector,
    val section: Section,
    val event: MainMenuEvent
)
