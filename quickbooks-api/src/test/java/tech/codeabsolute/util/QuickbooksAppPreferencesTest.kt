package tech.codeabsolute.util

//import io.mockk.every
//import io.mockk.impl.annotations.MockK
//import io.mockk.junit5.MockKExtension
//import io.mockk.justRun
//import io.mockk.verify
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import tech.codeabsolute.model.AuthCode
//import tech.codeabsolute.model.RealmId
//import tech.codeabsolute.model.Token
//import tech.codeabsolute.random
//import java.util.prefs.Preferences
//
//@ExtendWith(MockKExtension::class)
//class QuickbooksAppPreferencesTest {
//
//    @MockK
//    private lateinit var preferences: Preferences
//
//    private lateinit var appPreferences: AppPreferences
//
//    @BeforeEach
//    fun setUp() {
//        appPreferences = QuickbooksAppPreferences(preferences)
//    }
//
//    @Test
//    fun `Set auth code`() {
//
//        val authCodeValue = String.random()
//        val authCode = AuthCode(authCodeValue)
//        justRun { preferences.put(QuickbooksAppPreferences.KEY_AUTH_CODE, authCodeValue) }
//
//        appPreferences.setAuthCode(authCode)
//
//        verify { preferences.put(QuickbooksAppPreferences.KEY_AUTH_CODE, authCodeValue) }
//    }
//
//    @Test
//    fun `Get auth code`() {
//
//        val authCodeValue = String.random()
//        val expectedAuthCode = AuthCode(authCodeValue)
//        every {
//            preferences.get(
//                QuickbooksAppPreferences.KEY_AUTH_CODE,
//                String.empty()
//            )
//        } returns authCodeValue
//
//        val authCode = appPreferences.getAuthCode()
//
//        assertEquals(expectedAuthCode, authCode)
//    }
//
//    @Test
//    fun `Get null auth code`() {
//
//        val authCodeValue = String.empty()
//        val expectedAuthCode = null
//        every {
//            preferences.get(
//                QuickbooksAppPreferences.KEY_AUTH_CODE,
//                String.empty()
//            )
//        } returns authCodeValue
//
//        val authCode = appPreferences.getAuthCode()
//
//        assertEquals(expectedAuthCode, authCode)
//    }
//
//    @Test
//    fun `Set realm id`() {
//
//        val realmIdValue = String.random()
//        val realmId = RealmId(realmIdValue)
//        justRun { preferences.put(QuickbooksAppPreferences.KEY_REALM_ID, realmIdValue) }
//
//        appPreferences.setRealmId(realmId)
//
//        verify { preferences.put(QuickbooksAppPreferences.KEY_REALM_ID, realmIdValue) }
//    }
//
//    @Test
//    fun `Get realm id`() {
//
//        val realmIdValue = String.random()
//        val expectedRealmId = RealmId(realmIdValue)
//        every {
//            preferences.get(
//                QuickbooksAppPreferences.KEY_REALM_ID,
//                String.empty()
//            )
//        } returns realmIdValue
//
//        val realmId = appPreferences.getRealmId()
//
//        assertEquals(expectedRealmId, realmId)
//    }
//
//    @Test
//    fun `Get null realm id`() {
//
//        val realmIdValue = String.empty()
//        val expectedRealmId = null
//        every {
//            preferences.get(
//                QuickbooksAppPreferences.KEY_REALM_ID,
//                String.empty()
//            )
//        } returns realmIdValue
//
//        val realmId = appPreferences.getRealmId()
//
//        assertEquals(expectedRealmId, realmId)
//    }
//
//    @Test
//    fun `Set access token`() {
//
//        val tokenValue = String.random()
//        val token = Token(tokenValue)
//        justRun { preferences.put(QuickbooksAppPreferences.KEY_ACCESS_TOKEN, tokenValue) }
//
//        appPreferences.setAccessToken(token)
//
//        verify { preferences.put(QuickbooksAppPreferences.KEY_ACCESS_TOKEN, tokenValue) }
//    }
//
//    @Test
//    fun `Get access token`() {
//
//        val tokenValue = String.random()
//        val expectedToken = Token(tokenValue)
//        every {
//            preferences.get(
//                QuickbooksAppPreferences.KEY_ACCESS_TOKEN,
//                String.empty()
//            )
//        } returns tokenValue
//
//        val token = appPreferences.getAccessToken()
//
//        assertEquals(expectedToken, token)
//    }
//
//    @Test
//    fun `Get null access token`() {
//
//        val tokenValue = String.empty()
//        val expectedToken = null
//        every {
//            preferences.get(
//                QuickbooksAppPreferences.KEY_ACCESS_TOKEN,
//                String.empty()
//            )
//        } returns tokenValue
//
//        val token = appPreferences.getAccessToken()
//
//        assertEquals(expectedToken, token)
//    }
//
//    @Test
//    fun `Set refresh token`() {
//
//        val tokenValue = String.random()
//        val token = Token(tokenValue)
//        justRun { preferences.put(QuickbooksAppPreferences.KEY_REFRESH_TOKEN, tokenValue) }
//
//        appPreferences.setRefreshToken(token)
//
//        verify { preferences.put(QuickbooksAppPreferences.KEY_REFRESH_TOKEN, tokenValue) }
//    }
//
//    @Test
//    fun `Get refresh token`() {
//
//        val tokenValue = String.random()
//        val expectedToken = Token(tokenValue)
//        every {
//            preferences.get(
//                QuickbooksAppPreferences.KEY_REFRESH_TOKEN,
//                String.empty()
//            )
//        } returns tokenValue
//
//        val token = appPreferences.getRefreshToken()
//
//        assertEquals(expectedToken, token)
//    }
//
//    @Test
//    fun `Get null refresh token`() {
//
//        val tokenValue = String.empty()
//        val expectedToken = null
//        every {
//            preferences.get(
//                QuickbooksAppPreferences.KEY_REFRESH_TOKEN,
//                String.empty()
//            )
//        } returns tokenValue
//
//        val token = appPreferences.getRefreshToken()
//
//        assertEquals(expectedToken, token)
//    }
//}