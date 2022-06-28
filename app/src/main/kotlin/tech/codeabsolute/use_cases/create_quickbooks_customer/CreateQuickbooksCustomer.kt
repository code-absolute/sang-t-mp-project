package tech.codeabsolute.use_cases.create_quickbooks_customer

import tech.codeabsolute.model.Client
import tech.codeabsolute.repository.ClientsRepository

class CreateQuickbooksCustomer(
    private val clientsRepository: ClientsRepository
) : CreateQuickbooksCustomerUseCase {

    override fun invoke(input: Client?) {
        clientsRepository
    }
}