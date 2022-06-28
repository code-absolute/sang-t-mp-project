package tech.codeabsolute.use_cases.create_client

import tech.codeabsolute.model.invoice_item.Item
import tech.codeabsolute.use_cases.UseCase

interface GetRequisitionTypesUseCase : UseCase.WithOutputSuspending<List<Item>>