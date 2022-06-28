package tech.codeabsolute.use_cases.set_access_tokens

import tech.codeabsolute.client.OAuth2Client
import tech.codeabsolute.model.AuthResult
import java.net.URI

class SetQuickbooksAccessTokens(
    private val oAuth2Client: OAuth2Client
) : SetQuickbooksAccessTokensUseCase {

    override suspend fun invoke(input: URI): AuthResult<Unit> {
        return oAuth2Client.setAccessTokens(input)
    }
}