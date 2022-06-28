package tech.codeabsolute.presentation.clients

import tech.codeabsolute.model.Client
import tech.codeabsolute.model.SearchAttributes
import tech.codeabsolute.util.empty

data class ClientsSectionState(
    val isLoading: Boolean = false,
    val searchQuery: String = String.empty(),
    val searchFilter: SearchAttributes.Filter = SearchAttributes.Filter.MEDICARE_NUMBER,
    val searchOrder: SearchAttributes.Order = SearchAttributes.Order.ASCENDING,
    val clients: List<Client> = emptyList(),
)
