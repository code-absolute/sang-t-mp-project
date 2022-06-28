package tech.codeabsolute.use_cases.authenticate_local_user

import tech.codeabsolute.model.Account
import tech.codeabsolute.model.LoginCredentials
import tech.codeabsolute.use_cases.UseCase

interface AuthenticateLocalUserUseCase: UseCase.WithInputAndOutputSuspending<LoginCredentials, Account?>