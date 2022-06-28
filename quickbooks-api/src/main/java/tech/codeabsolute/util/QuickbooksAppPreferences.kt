package tech.codeabsolute.util

import tech.codeabsolute.model.AuthCode
import tech.codeabsolute.model.RealmId
import tech.codeabsolute.model.Token
import java.util.prefs.Preferences

class QuickbooksAppPreferences(
    private val preferences: Preferences
) : AppPreferences {

    override fun setAuthCode(authCode: AuthCode) {
        preferences.put(KEY_AUTH_CODE, authCode.value)
    }

    override fun getAuthCode(): AuthCode? =
        with(preferences.get(KEY_AUTH_CODE, String.empty())) {
            if (isEmpty()) return null
            return AuthCode(this)
        }

    override fun setRealmId(realmId: RealmId) {
        preferences.put(KEY_REALM_ID, realmId.value)
    }

    override fun getRealmId(): RealmId? =
        with(preferences.get(KEY_REALM_ID, String.empty())) {
            if (isEmpty()) return null
            return RealmId(this)
        }

    override fun setAccessToken(token: Token) {
        preferences.put(KEY_ACCESS_TOKEN, token.value)
    }

    override fun getAccessToken(): Token? =
        with(preferences.get(KEY_ACCESS_TOKEN, String.empty())) {
            if (isEmpty()) return null
            return Token(this)
        }

    override fun setRefreshToken(token: Token) {
        preferences.put(KEY_REFRESH_TOKEN, token.value)
    }

    override fun getRefreshToken(): Token? =
        with(preferences.get(KEY_REFRESH_TOKEN, String.empty())) {
            if (isEmpty()) return null
            return Token(this)
        }

    companion object {
        const val KEY_AUTH_CODE = "KEY_AUTH_CODE"
        const val KEY_REALM_ID = "KEY_REALM_ID"
        const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
        const val KEY_REFRESH_TOKEN = "KEY_REFRESH_TOKEN"
    }
}