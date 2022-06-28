package tech.codeabsolute.repository

import tech.codeabsolute.model.Account
import tech.codeabsolute.model.LoginCredentials

interface AuthenticationRepository {
    suspend fun login(loginCredentials: LoginCredentials): Account?
}