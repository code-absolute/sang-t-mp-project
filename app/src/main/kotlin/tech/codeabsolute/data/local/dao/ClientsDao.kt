package tech.codeabsolute.data.local.dao

import org.joda.time.DateTime
import tech.codeabsolute.model.Client
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.model.SearchAttributes

interface ClientsDao {
    suspend fun getClients(searchAttributes: SearchAttributes): List<Client>
    suspend fun insertClient(client: Client): Client
    fun insertReferral(requisition: Requisition, clientId: Int)
    fun getClient(clientId: Int): Client?
    fun getClientsForReport(
        from: DateTime,
        to: DateTime,
        filter: SearchAttributes.Filter,
        order: SearchAttributes.Order
    ): List<Client>

    suspend fun updateClientQuickbooksId(clientId: Int, quickbooksId: Int)
}