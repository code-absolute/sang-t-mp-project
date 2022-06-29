package tech.codeabsolute.presentation.client_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import tech.codeabsolute.data.local.dao.ClientsDao
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.use_cases.get_client.GetClientUseCase
import tech.codeabsolute.use_cases.update_requisitions.UpdateRequisitionsInput
import tech.codeabsolute.use_cases.update_requisitions.UpdateRequisitionsUseCase
import tech.codeabsolute.util.Resource

@Single
class ClientDetailsSectionViewModel(
    val getClientUseCase: GetClientUseCase,
    val updateRequisitionsUseCase: UpdateRequisitionsUseCase,
    val clientsDao: ClientsDao
) {

    var uiState by mutableStateOf(ClientDetailsSectionState())

    fun loadClient(clientId: Int?) {
        CoroutineScope(Dispatchers.IO).launch {
            getClientUseCase(clientId).collect {
                uiState = when (it) {
                    is Resource.Error -> uiState.copy(isLoading = false, errorMessage = it.message)
                    is Resource.Loading -> uiState.copy(isLoading = true)
                    is Resource.Success -> uiState.copy(isLoading = false, client = it.data)
                }
            }
        }
    }

    fun updateRequisitions(clientId: Int, requisition: Requisition) {
        updateRequisitionsUseCase(
            UpdateRequisitionsInput(
                clientId = clientId,
                requisition = requisition
            )
        )
    }

    fun deleteRequisition(requisition: Requisition) {
        clientsDao.deleteRequisition(requisition)
    }

    fun addRequisition(id: Int, requisition: Requisition) {
        uiState.client?.medicareNumber?.let {
            clientsDao.addRequisition(id, requisition, it)
        }
    }
}