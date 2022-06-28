package tech.codeabsolute.use_cases.authenticate_user

import tech.codeabsolute.client.OAuth2Client
import tech.codeabsolute.model.AuthResult

class AuthenticateQuickbooksUser(
    private val oAuth2Client: OAuth2Client
) : AuthenticateQuickbooksUserUseCase {

    override suspend fun invoke(): AuthResult<Unit> {
        return oAuth2Client.authenticate()
    }
}