package tech.codeabsolute.use_cases.get_requisition_types

import tech.codeabsolute.model.invoice_item.Item
import tech.codeabsolute.use_cases.UseCase

interface GetRequisitionTypesUseCase : UseCase.WithOutputSuspending<List<Item>>