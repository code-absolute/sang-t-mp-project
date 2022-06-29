package tech.codeabsolute.use_cases.create_invoice

import tech.codeabsolute.model.Requisition

data class CreateInvoiceInput(
    val quickbooksClientId: Int,
    val requisition: Requisition,
)
