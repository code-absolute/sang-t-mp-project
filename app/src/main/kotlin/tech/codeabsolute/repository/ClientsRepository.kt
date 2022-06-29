package tech.codeabsolute.repository

import kotlinx.coroutines.flow.Flow
import tech.codeabsolute.model.Client
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.model.SearchAttributes
import tech.codeabsolute.model.invoice_item.Item
import tech.codeabsolute.util.Resource

interface ClientsRepository {
    fun getClient(clientId: Int?): Flow<Resource<Client>>
    fun getClients(searchAttributes: SearchAttributes): Flow<Resource<List<Client>>>
    fun createClient(client: Client): Flow<Resource<Client>>
    suspend fun getRequisitionTypes(): List<Item>
    fun createInvoice(quickbooksClientId: Int, requisition: Requisition): Flow<Resource<String>>

    fun updateRequisitions(clientId: Int, requisition: Requisition)
}