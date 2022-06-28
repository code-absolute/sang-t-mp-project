package tech.codeabsolute.use_cases.create_client

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import tech.codeabsolute.model.Client
import tech.codeabsolute.repository.ClientsRepository
import tech.codeabsolute.util.Resource

@Single
class CreateClient(
    private val clientsRepository: ClientsRepository
) : CreateClientUseCase {

    override fun invoke(input: Client): Flow<Resource<Client>> {
        return clientsRepository.createClient(input)
    }
}
