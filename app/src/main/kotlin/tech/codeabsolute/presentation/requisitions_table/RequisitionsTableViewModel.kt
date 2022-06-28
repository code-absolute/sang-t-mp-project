package tech.codeabsolute.presentation.requisitions_table

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class RequisitionsTableViewModel {

    var uiState by mutableStateOf(RequisitionsTableState())

    fun onEvent(event: RequisitionsTableEvent) {
        when (event) {
            RequisitionsTableEvent.CloseAddRequisitionDialog -> {
                uiState = uiState.copy(
                    isAddRequisitionDialogOpen = false,
                    requisitionToEdit = null
                )
            }
            RequisitionsTableEvent.OpenAddRequisitionDialog -> {
                uiState = uiState.copy(isAddRequisitionDialogOpen = true)
            }
            is RequisitionsTableEvent.AddRequisition -> {
                uiState = uiState.copy(
                    requisitions = uiState.requisitions.toMutableList().apply {
                        add(event.requisition)
                    }
                )
                event.onRequisitionsListChanged(uiState.requisitions)
            }
            is RequisitionsTableEvent.OpenEditRequisitionDialog -> {
                uiState = uiState.copy(
                    isAddRequisitionDialogOpen = true,
                    requisitionToEdit = event.requisition
                )
            }
            is RequisitionsTableEvent.EditRequisition -> {
                uiState = uiState.copy(
                    requisitions = uiState.requisitions.toMutableList().apply {
                        indexOfFirst {
                            it.id == event.requisition.id
                        }.let {
                            set(it, event.requisition)
                        }
                    }
                )
                event.onRequisitionsListChanged(uiState.requisitions)
            }
            is RequisitionsTableEvent.LoadExistingRequisitions -> {
                if (event.requisitions == null) return
                uiState = uiState.copy(
                    requisitions = event.requisitions
                )
            }
        }
    }
}