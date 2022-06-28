package tech.codeabsolute.presentation.requisitions_table

import tech.codeabsolute.model.Requisition

sealed class RequisitionsTableEvent {
    object OpenAddRequisitionDialog : RequisitionsTableEvent()
    object CloseAddRequisitionDialog : RequisitionsTableEvent()
    data class AddRequisition(
        val requisition: Requisition,
        val onRequisitionsListChanged: (List<Requisition>) -> Unit
    ) : RequisitionsTableEvent()

    data class EditRequisition(
        val requisition: Requisition,
        val onRequisitionsListChanged: (List<Requisition>) -> Unit
    ) : RequisitionsTableEvent()

    data class OpenEditRequisitionDialog(val requisition: Requisition) : RequisitionsTableEvent()
    data class LoadExistingRequisitions(val requisitions: List<Requisition>?) : RequisitionsTableEvent()
}
