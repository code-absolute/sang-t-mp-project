package tech.codeabsolute.client

import tech.codeabsolute.model.AuthResult
import tech.codeabsolute.model.Url
import java.net.URI

interface OAuth2Client {
    fun getAuthorizationRequestUrl(): Url
    suspend fun setAccessTokens(uri: URI): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}