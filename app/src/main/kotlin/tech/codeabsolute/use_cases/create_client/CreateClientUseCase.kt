package tech.codeabsolute.use_cases.create_client

import kotlinx.coroutines.flow.Flow
import tech.codeabsolute.model.Client
import tech.codeabsolute.use_cases.UseCase
import tech.codeabsolute.util.Resource

interface CreateClientUseCase : UseCase.WithInputAndOutput<Client, Flow<Resource<Client>>>