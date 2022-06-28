package tech.codeabsolute.client

//import com.intuit.oauth2.client.OAuth2PlatformClient
//import com.intuit.oauth2.config.OAuth2Config
//import com.intuit.oauth2.config.Scope
//import com.intuit.oauth2.data.BearerTokenResponse
//import com.intuit.oauth2.exception.OAuthException
//import io.mockk.every
//import io.mockk.impl.annotations.MockK
//import io.mockk.junit5.MockKExtension
//import io.mockk.justRun
//import io.mockk.mockk
//import io.mockk.verify
//import kotlinx.coroutines.runBlocking
//import org.apache.http.NameValuePair
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Assertions.assertTrue
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import org.junit.jupiter.api.extension.ExtendWith
//import tech.codeabsolute.model.*
//import tech.codeabsolute.random
//import tech.codeabsolute.util.AppPreferences
//import tech.codeabsolute.util.URIUtils
//import java.net.URI
//
//@ExtendWith(MockKExtension::class)
//class QuickbooksOAuth2ClientTest {
//
//    @MockK
//    private lateinit var oAuth2Config: OAuth2Config
//
//    @MockK
//    private lateinit var oAuth2PlatformClient: OAuth2PlatformClient
//
//    @MockK
//    private lateinit var appPreferences: AppPreferences
//
//    @MockK
//    private lateinit var uriUtils: URIUtils
//
//    private lateinit var client: OAuth2Client
//
//    @BeforeEach
//    fun setUp() {
//        client = QuickbooksOAuth2Client(
//            oAuth2Config,
//            oAuth2PlatformClient,
//            appPreferences,
//            uriUtils
//        )
//    }
//
//    @Test
//    fun `Get authorization request url`() {
//
//        val urlString = "https://${String.random()}"
//        val expectedUrl = Url(urlString)
//        val scopes = listOf(Scope.Accounting)
//        val redirectUri = QuickbooksOAuth2Client.REDIRECT_URI
//        every { oAuth2Config.prepareUrl(scopes, redirectUri) } returns urlString
//
//        val url = client.getAuthorizationRequestUrl()
//
//        assertEquals(expectedUrl, url)
//    }
//
//    @Test
//    fun `Set access tokens returns AuthResult Unauthorized when retrieveBearerTokens throws OAuthException`() =
//        runBlocking {
//
//            val attributes = getAttributes()
//            val authCodeValue = attributes[0].value
//            val realmIdValue = attributes[1].value
//            val uri = URI(String.random())
//            every { uriUtils.getAttributesFromURI(uri) } returns attributes
//            justRun { appPreferences.setAuthCode(AuthCode(authCodeValue)) }
//            justRun { appPreferences.setRealmId(RealmId(realmIdValue)) }
//            every { appPreferences.getAuthCode() } returns AuthCode(authCodeValue)
//            every {
//                oAuth2PlatformClient.retrieveBearerTokens(
//                    authCodeValue,
//                    QuickbooksOAuth2Client.REDIRECT_URI
//                )
//            } throws OAuthException(String.random())
//            val authResult = client.setAccessTokens(uri)
//
//            assertThrows<OAuthException> {
//                oAuth2PlatformClient.retrieveBearerTokens(
//                    authCodeValue,
//                    QuickbooksOAuth2Client.REDIRECT_URI
//                )
//            }
//            assertTrue(authResult is AuthResult.Unauthorized)
//        }
//
//    private fun getAttributes(): MutableList<NameValuePair> {
//        val authCodeValue = String.random()
//        val realmIdValue = String.random()
//        return mutableListOf(
//            object : NameValuePair {
//                override fun getName(): String = "code"
//                override fun getValue(): String = authCodeValue
//            },
//            object : NameValuePair {
//                override fun getName(): String = "realmId"
//                override fun getValue(): String = realmIdValue
//            }
//        )
//    }
//
//    @Test
//    fun `Set access tokens`() = runBlocking {
//
//        val expectedAccessTokenValue = String.random()
//        val expectedRefreshTokenValue = String.random()
//        val expectedAccessToken = Token(expectedAccessTokenValue)
//        val expectedRefreshToken = Token(expectedRefreshTokenValue)
//
//        val attributes = getAttributes()
//        val authCodeValue = attributes[0].value
//        val realmIdValue = attributes[1].value
//        val uri = URI(String.random())
//        every { uriUtils.getAttributesFromURI(uri) } returns attributes
//        justRun { appPreferences.setAuthCode(AuthCode(authCodeValue)) }
//        justRun { appPreferences.setRealmId(RealmId(realmIdValue)) }
//        every { appPreferences.getAuthCode() } returns AuthCode(authCodeValue)
//        val bearerTokenResponse = mockk<BearerTokenResponse>()
//        every { appPreferences.getAuthCode() } returns AuthCode(authCodeValue)
//        every {
//            oAuth2PlatformClient.retrieveBearerTokens(
//                authCodeValue,
//                QuickbooksOAuth2Client.REDIRECT_URI
//            )
//        } returns bearerTokenResponse
//        every { bearerTokenResponse.accessToken } returns expectedAccessTokenValue
//        every { bearerTokenResponse.refreshToken } returns expectedRefreshTokenValue
//        justRun { appPreferences.setAuthCode(AuthCode(authCodeValue)) }
//        justRun { appPreferences.setRealmId(RealmId(realmIdValue)) }
//        justRun { appPreferences.setAccessToken(expectedAccessToken) }
//        justRun { appPreferences.setRefreshToken(expectedRefreshToken) }
//
//        val authResult = client.setAccessTokens(uri)
//
//        verify {
//            appPreferences.setAccessToken(expectedAccessToken)
//            appPreferences.setRefreshToken(expectedRefreshToken)
//        }
//        assertTrue(authResult is AuthResult.Authorized)
//    }
//
//    @Test
//    fun `Authenticate returns AuthResult Unauthorized when refresh token is empty`() = runBlocking {
//
//        every { appPreferences.getRefreshToken() } returns null
//
//        val authResult = client.authenticate()
//
//        assertTrue(authResult is AuthResult.Unauthorized)
//    }
//
//    @Test
//    fun `Authenticate returns AuthResult Unauthorized when token refresh throws OAuthException`() = runBlocking {
//
//        val oldRefreshTokenValue = String.random()
//        val oldRefreshToken = Token(oldRefreshTokenValue)
//        every { appPreferences.getRefreshToken() } returns oldRefreshToken
//        every { oAuth2PlatformClient.refreshToken(oldRefreshTokenValue) } throws OAuthException(String.random())
//
//        val authResult = client.authenticate()
//
//        assertThrows<OAuthException> { oAuth2PlatformClient.refreshToken(oldRefreshTokenValue) }
//        assertTrue(authResult is AuthResult.Unauthorized)
//    }
//
//    @Test
//    fun `Authenticate returns AuthResult Authorized when refresh token is available`() = runBlocking {
//
//        val oldRefreshTokenValue = String.random()
//        val oldRefreshToken = Token(oldRefreshTokenValue)
//        val expectedAccessTokenValue = String.random()
//        val expectedAccessToken = Token(expectedAccessTokenValue)
//        val expectedRefreshTokenValue = String.random()
//        val expectedRefreshToken = Token(expectedRefreshTokenValue)
//        val bearerTokenResponse = mockk<BearerTokenResponse>()
//        every { appPreferences.getRefreshToken() } returns oldRefreshToken
//        every { oAuth2PlatformClient.refreshToken(oldRefreshTokenValue) } returns bearerTokenResponse
//        every { bearerTokenResponse.accessToken } returns expectedAccessTokenValue
//        every { bearerTokenResponse.refreshToken } returns expectedRefreshTokenValue
//        justRun { appPreferences.setAccessToken(expectedAccessToken) }
//        justRun { appPreferences.setRefreshToken(expectedRefreshToken) }
//
//        val authResult = client.authenticate()
//
//        verify {
//            oAuth2PlatformClient.refreshToken(oldRefreshTokenValue)
//            appPreferences.setAccessToken(expectedAccessToken)
//            appPreferences.setRefreshToken(expectedRefreshToken)
//        }
//        assertTrue(authResult is AuthResult.Authorized)
//    }
//}