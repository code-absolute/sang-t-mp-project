package tech.codeabsolute.presentation.requisitions_table

import tech.codeabsolute.model.Requisition

data class RequisitionsTableState(
    val isLoading: Boolean = false,
    val requisitions: List<Requisition> = listOf(),
    val isAddRequisitionDialogOpen: Boolean = false,
    val requisitionToEdit: Requisition? = null
)
