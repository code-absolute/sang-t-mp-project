package tech.codeabsolute.presentation.requisitions_table

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.use_cases.create_invoice.CreateInvoiceInput
import tech.codeabsolute.use_cases.create_invoice.CreateInvoiceUseCase

class RequisitionsTableViewModel(
    val createInvoiceUseCase: CreateInvoiceUseCase
) {

    var uiState by mutableStateOf(RequisitionsTableState())

    fun onEvent(event: RequisitionsTableEvent) {
        when (event) {
            RequisitionsTableEvent.CloseAddRequisitionDialog -> {
                uiState = uiState.copy(
                    isAddRequisitionDialogOpen = false,
                    requisitionToEditIndex = null
                )
            }
            is RequisitionsTableEvent.OpenAddRequisitionDialog -> {
                uiState = uiState.copy(isAddRequisitionDialogOpen = true)
            }
            is RequisitionsTableEvent.AddRequisition -> {
                uiState = uiState.copy(
                    requisitions = uiState.requisitions.toMutableList().apply {
                        add(event.requisition)
                    }
                )
                event.onRequisitionsListChanged(event.requisition, uiState.requisitions)
                event.onAddRequisition(event.requisition)
            }
            is RequisitionsTableEvent.OpenEditRequisitionDialog -> {
                uiState = uiState.copy(
                    isAddRequisitionDialogOpen = true,
                    requisitionToEditIndex = event.requisitionIndex
                )
            }
            is RequisitionsTableEvent.EditRequisition -> {
                uiState = uiState.copy(
                    requisitions = uiState.requisitions.toMutableList().apply {
                        uiState.requisitionToEditIndex?.let { set(it, event.requisition) }
                    }
                )
                event.onRequisitionsListChanged(event.requisition, uiState.requisitions)
            }
            is RequisitionsTableEvent.LoadExistingRequisitions -> {
                uiState = uiState.copy(
                    requisitions = event.requisitions ?: emptyList()
                )
            }
            is RequisitionsTableEvent.OnDeleteRequisition -> {
                uiState = uiState.copy(
                    requisitions = uiState.requisitions.toMutableList().apply {
                        remove(event.requisition)
                    }
                )
                event.onRequisitionsListChanged(event.requisition, uiState.requisitions)
            }
        }
    }

    private fun createInvoice(id: Int, requisition: Requisition) {
        createInvoiceUseCase(
            CreateInvoiceInput(
                quickbooksClientId = id,
                requisition = requisition,
            )
        )
    }
}