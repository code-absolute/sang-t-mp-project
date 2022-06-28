package tech.codeabsolute.presentation.new_client

interface NewClientSectionViewModel {
    var uiState: NewClientSectionState
    fun onEvent(event: NewClientSectionEvent)
}