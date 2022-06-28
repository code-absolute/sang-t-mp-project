package tech.codeabsolute.presentation.requisitions_table.add_requisition_dialog

import androidx.compose.ui.text.input.TextFieldValue
import tech.codeabsolute.model.invoice_item.Item
import tech.codeabsolute.util.empty

data class AddRequisitionDialogState(
    val isLoading: Boolean = false,
    val requisitionPath: String = String.empty(),
    val displayedRequisitionPath: String = String.empty(),
    val requisitionTypes: List<Item> = listOf(),
    val requisitionType: Item = Item(),
    val isTested: Boolean = false,
    val testDateTextFieldValue: TextFieldValue = TextFieldValue(),
    val testTimeTextFieldValue: TextFieldValue = TextFieldValue(),
    val testAMPM: String = "AM"
)