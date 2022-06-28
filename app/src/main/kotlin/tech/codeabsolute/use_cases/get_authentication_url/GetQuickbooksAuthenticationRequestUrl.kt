package tech.codeabsolute.use_cases.get_authentication_url

import tech.codeabsolute.client.OAuth2Client
import tech.codeabsolute.model.Url

class GetQuickbooksAuthenticationRequestUrl(
    private val oAuth2Client: OAuth2Client
) : GetQuickbooksAuthenticationRequestUrlUseCase {

    override suspend fun invoke(): Url {
        return oAuth2Client.getAuthorizationRequestUrl()
    }
}