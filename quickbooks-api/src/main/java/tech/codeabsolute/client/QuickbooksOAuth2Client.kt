package tech.codeabsolute.client

import com.intuit.oauth2.client.OAuth2PlatformClient
import com.intuit.oauth2.config.OAuth2Config
import com.intuit.oauth2.config.Scope
import com.intuit.oauth2.data.BearerTokenResponse
import com.intuit.oauth2.exception.OAuthException
import tech.codeabsolute.model.*
import tech.codeabsolute.util.AppPreferences
import tech.codeabsolute.util.URIUtils
import java.net.URI

class QuickbooksOAuth2Client(
    private val config: OAuth2Config,
    private val oAuth2PlatformClient: OAuth2PlatformClient,
    private val appPreferences: AppPreferences,
    private val uriUtils: URIUtils
) : OAuth2Client {

    override fun getAuthorizationRequestUrl(): Url = Url(
        config.prepareUrl(
            listOf(Scope.Accounting),
            REDIRECT_URI
        )
    )

    override suspend fun setAccessTokens(uri: URI): AuthResult<Unit> {
        return try {
            uriUtils.getAttributesFromURI(uri).forEach {
                when (it.name) {
                    "code" -> appPreferences.setAuthCode(AuthCode(it.value))
                    "realmId" -> appPreferences.setRealmId(RealmId(it.value))
                }
            }
            val bearerTokenResponse =
                oAuth2PlatformClient.retrieveBearerTokens(appPreferences.getAuthCode()?.value, REDIRECT_URI)
            assignTokens(bearerTokenResponse)
            AuthResult.Authorized()
        } catch (e: OAuthException) {
            AuthResult.Unauthorized()
        }
    }

    private fun assignTokens(bearerTokenResponse: BearerTokenResponse) {
        appPreferences.setAccessToken(Token(bearerTokenResponse.accessToken))
        appPreferences.setRefreshToken(Token(bearerTokenResponse.refreshToken))
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        //resetTokens()
        try {
            val refreshToken = appPreferences.getRefreshToken() ?: return AuthResult.Unauthorized()
            val bearerTokenResponse = oAuth2PlatformClient.refreshToken(refreshToken.value)
            assignTokens(bearerTokenResponse)
            return AuthResult.Authorized()
        } catch (e: OAuthException) {
            return AuthResult.Unauthorized()
        }
    }

    private fun resetTokens() {
        appPreferences.setAccessToken(Token(""))
        appPreferences.setRefreshToken(Token(""))
    }

    companion object {
        const val REDIRECT_URI = "https://codeabsolute.tech"
    }
}