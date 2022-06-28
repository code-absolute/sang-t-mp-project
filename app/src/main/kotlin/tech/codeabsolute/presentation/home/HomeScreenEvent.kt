package tech.codeabsolute.presentation.home

import tech.codeabsolute.presentation.main_menu.Section

sealed class HomeScreenEvent {
    data class ChangeSection(val section: Section) : HomeScreenEvent()
}
