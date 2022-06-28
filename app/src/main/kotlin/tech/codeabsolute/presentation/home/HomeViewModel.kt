package tech.codeabsolute.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class HomeViewModel {

    var uiState by mutableStateOf(HomeViewState())
        private set

    fun onEvent(event: HomeScreenEvent) {
        uiState = when (event) {
            is HomeScreenEvent.ChangeSection -> uiState.copy(section = event.section)
        }
    }
}