package tech.codeabsolute.use_cases.update_requisitions

import tech.codeabsolute.model.Requisition

data class UpdateRequisitionsInput(
    val clientId: Int,
    val requisition: Requisition
)
