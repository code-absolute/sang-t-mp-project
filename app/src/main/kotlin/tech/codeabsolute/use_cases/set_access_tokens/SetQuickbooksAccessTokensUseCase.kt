package tech.codeabsolute.use_cases.set_access_tokens

import tech.codeabsolute.model.AuthResult
import tech.codeabsolute.use_cases.UseCase
import java.net.URI

interface SetQuickbooksAccessTokensUseCase : UseCase.WithInputAndOutputSuspending<URI, AuthResult<Unit>>