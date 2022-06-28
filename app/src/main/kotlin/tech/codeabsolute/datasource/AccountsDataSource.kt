package tech.codeabsolute.datasource

import tech.codeabsolute.model.Account

interface AccountsDataSource {
    fun createAccount(account: Account): Boolean
    suspend fun getAccount(username: String, password: String): Account?
}