package tech.codeabsolute.use_cases.authenticate_user

import tech.codeabsolute.model.AuthResult
import tech.codeabsolute.use_cases.UseCase

interface AuthenticateQuickbooksUserUseCase : UseCase.WithOutputSuspending<AuthResult<Unit>>