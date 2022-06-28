package tech.codeabsolute.presentation.clients

import tech.codeabsolute.model.SearchAttributes

sealed class ClientsSectionEvent {
    data class OnSearchQueryChanged(val searchQuery: String) : ClientsSectionEvent()
    data class OnSearchFilterChanged(val filter: SearchAttributes.Filter) : ClientsSectionEvent()
    data class OnSearchOrderChanged(val order: SearchAttributes.Order) : ClientsSectionEvent()
}
