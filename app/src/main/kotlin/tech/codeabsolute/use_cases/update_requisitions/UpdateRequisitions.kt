package tech.codeabsolute.use_cases.update_requisitions

import org.koin.core.annotation.Single
import tech.codeabsolute.repository.ClientsRepository

@Single
class UpdateRequisitions(
    private val clientsRepository: ClientsRepository
) : UpdateRequisitionsUseCase {

    override fun invoke(input: UpdateRequisitionsInput) {
        clientsRepository.updateRequisitions(input.clientId, input.requisition)
    }
}