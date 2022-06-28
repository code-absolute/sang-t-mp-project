package tech.codeabsolute.util

import tech.codeabsolute.model.AuthCode
import tech.codeabsolute.model.RealmId
import tech.codeabsolute.model.Token

interface AppPreferences {
    fun setAuthCode(authCode: AuthCode)
    fun getAuthCode(): AuthCode?
    fun setRealmId(realmId: RealmId)
    fun getRealmId(): RealmId?
    fun setAccessToken(token: Token)
    fun getAccessToken(): Token?
    fun setRefreshToken(token: Token)
    fun getRefreshToken(): Token?
}