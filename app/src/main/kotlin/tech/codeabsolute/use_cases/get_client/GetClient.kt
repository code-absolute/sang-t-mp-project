package tech.codeabsolute.use_cases.get_client

import kotlinx.coroutines.flow.Flow
import tech.codeabsolute.model.Client
import tech.codeabsolute.repository.ClientsRepository
import tech.codeabsolute.util.Resource

class GetClient(
    private val clientsRepository: ClientsRepository
) : GetClientUseCase {

    override fun invoke(input: Int?): Flow<Resource<Client>> {
        return clientsRepository.getClient(input)
    }
}