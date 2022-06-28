package tech.codeabsolute.use_cases.authenticate_local_user

import tech.codeabsolute.model.Account
import tech.codeabsolute.model.LoginCredentials
import tech.codeabsolute.repository.AuthenticationRepository

class AuthenticateLocalUser(
    private val authenticationRepository: AuthenticationRepository
) : AuthenticateLocalUserUseCase {

    override suspend fun invoke(input: LoginCredentials): Account? {
        return authenticationRepository.login(input)
    }
}