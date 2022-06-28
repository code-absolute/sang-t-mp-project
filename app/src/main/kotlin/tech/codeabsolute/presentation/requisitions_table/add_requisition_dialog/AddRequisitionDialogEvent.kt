package tech.codeabsolute.presentation.requisitions_table.add_requisition_dialog

import tech.codeabsolute.model.Requisition
import tech.codeabsolute.model.invoice_item.Item

sealed class AddRequisitionDialogEvent {
    data class InitDialog(val requisition: Requisition?): AddRequisitionDialogEvent()
    object GetRequisitionTypes : AddRequisitionDialogEvent()
    data class OnFileSelected(val path: String) : AddRequisitionDialogEvent()
    data class OnTypeChanged(val type: Item) : AddRequisitionDialogEvent()
    data class OnAddClicked(val onAddRequisition: (Requisition) -> Unit) : AddRequisitionDialogEvent()
    data class OnIsTestedCheckChanged(val checked: Boolean) : AddRequisitionDialogEvent()
    data class OnTestDateChanged(val date: String) : AddRequisitionDialogEvent()
    data class OnTestTimeChanged(val time: String) : AddRequisitionDialogEvent()
    data class OnTestAMPMChanged(val value: String) : AddRequisitionDialogEvent()
}
