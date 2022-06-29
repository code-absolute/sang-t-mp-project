package tech.codeabsolute.use_cases.create_invoice

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import tech.codeabsolute.repository.ClientsRepository
import tech.codeabsolute.util.Resource

@Single
class CreateInvoice(
    private val clientsRepository: ClientsRepository
) : CreateInvoiceUseCase {

    override fun invoke(input: CreateInvoiceInput): Flow<Resource<String>> {
        return clientsRepository.createInvoice(input.quickbooksClientId, input.requisition)
    }
}