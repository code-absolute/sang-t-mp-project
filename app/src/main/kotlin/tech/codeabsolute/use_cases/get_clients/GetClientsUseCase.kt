package tech.codeabsolute.use_cases.get_clients

import kotlinx.coroutines.flow.Flow
import tech.codeabsolute.model.Client
import tech.codeabsolute.model.SearchAttributes
import tech.codeabsolute.use_cases.UseCase
import tech.codeabsolute.util.Resource

interface GetClientsUseCase : UseCase.WithInputAndOutput<SearchAttributes, Flow<Resource<List<Client>>>>