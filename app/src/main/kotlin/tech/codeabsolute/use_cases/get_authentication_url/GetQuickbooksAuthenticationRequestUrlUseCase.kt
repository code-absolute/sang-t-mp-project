package tech.codeabsolute.use_cases.get_authentication_url

import tech.codeabsolute.model.Url
import tech.codeabsolute.use_cases.UseCase

interface GetQuickbooksAuthenticationRequestUrlUseCase : UseCase.WithOutputSuspending<Url>