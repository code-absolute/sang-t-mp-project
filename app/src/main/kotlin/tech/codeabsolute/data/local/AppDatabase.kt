package tech.codeabsolute.data.local

import tech.codeabsolute.model.Account
import tech.codeabsolute.model.Client
import tech.codeabsolute.model.MedicareNumber
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.util.Resource

interface AppDatabase {
    fun connect()
    suspend fun build(): Resource<Unit>
    fun insertAccount(account: Account): Boolean
    suspend fun getAccount(username: String, password: String): Account?
    fun getClients(): List<Client>
    fun insertClient(client: Client): Client
    fun insertRequisition(requisition: Requisition, clientId: Int, medicareNumber: MedicareNumber)
    fun getClient(clientId: Int): Client?
    suspend fun updateClientQuickbooksId(clientId: Int, quickbooksId: Int)
    fun updateClientRequisitions(clientId: Int, requisition: Requisition)
    fun deleteRequisition(requisition: Requisition)
    fun addRequisition(clientId: Int, requisition: Requisition, medicareNumber: MedicareNumber)
}