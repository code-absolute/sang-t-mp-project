package tech.codeabsolute.use_cases.get_clients

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import tech.codeabsolute.model.Client
import tech.codeabsolute.model.SearchAttributes
import tech.codeabsolute.repository.ClientsRepository
import tech.codeabsolute.util.Resource

@Single
class GetClients(
    private val clientsRepository: ClientsRepository
) : GetClientsUseCase {

    override fun invoke(input: SearchAttributes): Flow<Resource<List<Client>>> {
        return clientsRepository.getClients(input)
    }
}