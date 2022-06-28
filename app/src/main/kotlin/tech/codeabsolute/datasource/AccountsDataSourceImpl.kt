package tech.codeabsolute.datasource

import tech.codeabsolute.data.local.AppDatabase
import tech.codeabsolute.encryption.DataEncryption
import tech.codeabsolute.model.Account

class AccountsDataSourceImpl(
    private val appDatabase: AppDatabase,
    private val dataEncryption: DataEncryption
) : AccountsDataSource {

    override fun createAccount(account: Account): Boolean {
        val encryptedAccount = account.copy(
            username = dataEncryption.encrypt(account.username),
            password = dataEncryption.encrypt(account.password)
        )
        return appDatabase.insertAccount(encryptedAccount)
    }

    override suspend fun getAccount(username: String, password: String): Account? {
        return appDatabase.getAccount(
            dataEncryption.encrypt(username),
            dataEncryption.encrypt(password)
        )
    }
}
