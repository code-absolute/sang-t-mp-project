package tech.codeabsolute.repository

import org.koin.core.annotation.Single
import tech.codeabsolute.datasource.AccountsDataSource
import tech.codeabsolute.model.Account
import tech.codeabsolute.model.LoginCredentials

class AuthenticationRepositoryImpl(
    private val accountsDataSource: AccountsDataSource
) : AuthenticationRepository {

    override suspend fun login(loginCredentials: LoginCredentials): Account? {
        return accountsDataSource.getAccount(loginCredentials.username, loginCredentials.password)
    }
}