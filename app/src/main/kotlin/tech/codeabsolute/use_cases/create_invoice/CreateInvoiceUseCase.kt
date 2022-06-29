package tech.codeabsolute.use_cases.create_invoice

import kotlinx.coroutines.flow.Flow
import tech.codeabsolute.use_cases.UseCase
import tech.codeabsolute.util.Resource

interface CreateInvoiceUseCase : UseCase.WithInputAndOutput<CreateInvoiceInput, Flow<Resource<String>>>