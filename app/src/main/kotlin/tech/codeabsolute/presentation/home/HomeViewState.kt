package tech.codeabsolute.presentation.home

import tech.codeabsolute.presentation.main_menu.Section

data class HomeViewState(
    val section: Section = Section.Clients
)
