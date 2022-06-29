package tech.codeabsolute.presentation.requisitions_table

import tech.codeabsolute.model.Requisition

sealed class RequisitionsTableEvent {
    object OpenAddRequisitionDialog : RequisitionsTableEvent()
    object CloseAddRequisitionDialog : RequisitionsTableEvent()
    data class AddRequisition(
        val requisition: Requisition,
        val onRequisitionsListChanged: (Requisition, List<Requisition>) -> Unit,
        val onAddRequisition: (Requisition) -> Unit
    ) : RequisitionsTableEvent()

    data class EditRequisition(
        val requisition: Requisition,
        val onRequisitionsListChanged: (Requisition, List<Requisition>) -> Unit
    ) : RequisitionsTableEvent()

    data class OpenEditRequisitionDialog(val requisitionIndex: Int) : RequisitionsTableEvent()
    data class LoadExistingRequisitions(val requisitions: List<Requisition>?) : RequisitionsTableEvent()
    data class OnDeleteRequisition(
        val requisition: Requisition,
        val onRequisitionsListChanged: (Requisition, List<Requisition>) -> Unit
    ) : RequisitionsTableEvent()
}
