package tech.codeabsolute.use_cases.get_client

import kotlinx.coroutines.flow.Flow
import tech.codeabsolute.model.Client
import tech.codeabsolute.use_cases.UseCase
import tech.codeabsolute.util.Resource

interface GetClientUseCase : UseCase.WithInputAndOutput<Int?, Flow<Resource<Client>>>