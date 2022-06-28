package tech.codeabsolute.use_cases.create_client

import org.koin.core.annotation.Single
import tech.codeabsolute.model.invoice_item.Item
import tech.codeabsolute.repository.ClientsRepository

@Single
class GetRequisitionTypes(
    private val clientsRepository: ClientsRepository
) : GetRequisitionTypesUseCase {
    override suspend fun invoke(): List<Item> = clientsRepository.getRequisitionTypes()
}