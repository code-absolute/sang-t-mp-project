package tech.codeabsolute.presentation.clients

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.codeabsolute.model.SearchAttributes
import tech.codeabsolute.use_cases.get_clients.GetClientsUseCase
import tech.codeabsolute.util.Resource

class ClientsSectionViewModel(
    private val getClientsUseCase: GetClientsUseCase,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) {

    var uiState by mutableStateOf(ClientsSectionState())
        private set

    fun onEvent(event: ClientsSectionEvent) {
        uiState = when (event) {
            is ClientsSectionEvent.OnSearchQueryChanged -> uiState.copy(searchQuery = event.searchQuery)
            is ClientsSectionEvent.OnSearchFilterChanged -> uiState.copy(searchFilter = event.filter)
            is ClientsSectionEvent.OnSearchOrderChanged -> uiState.copy(searchOrder = event.order)
        }
        search()
    }

    private fun search() {
        CoroutineScope(mainDispatcher).launch {
            getClientsUseCase(
                SearchAttributes(
                    searchTerm = uiState.searchQuery,
                    filter = uiState.searchFilter,
                    order = uiState.searchOrder
                )
            ).collect {
                uiState = when (it) {
                    is Resource.Error -> uiState.copy(isLoading = false, clients = emptyList())
                    is Resource.Loading -> uiState.copy(isLoading = true, clients = emptyList())
                    is Resource.Success -> uiState.copy(isLoading = false, clients = it.data ?: emptyList())
                }
            }
        }
    }
}