package tech.codeabsolute.data.local.dao

import org.joda.time.DateTime
import org.koin.core.annotation.Single
import tech.codeabsolute.data.local.AppDatabase
import tech.codeabsolute.model.Client
import tech.codeabsolute.model.MedicareNumber
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.model.SearchAttributes

@Single
class ClientsDaoImpl(
    private val appDatabase: AppDatabase
) : ClientsDao {

    override suspend fun getClients(searchAttributes: SearchAttributes): List<Client> {
        val clients = appDatabase.getClients().filter { patient ->
            when (searchAttributes.filter) {
                SearchAttributes.Filter.NAME -> patient.fullName.lastName.startsWith(
                    searchAttributes.searchTerm,
                    ignoreCase = true
                )
                SearchAttributes.Filter.MEDICARE_NUMBER -> patient.medicareNumber.number.startsWith(
                    searchAttributes.searchTerm,
                    ignoreCase = true
                )
            }
        }

        return when (searchAttributes.order) {
            SearchAttributes.Order.ASCENDING -> clients.sortedBy {
                when (searchAttributes.filter) {
                    SearchAttributes.Filter.NAME -> it.fullName.lastName
                    SearchAttributes.Filter.MEDICARE_NUMBER -> it.medicareNumber.number
                }
            }
            SearchAttributes.Order.DESCENDING -> clients.sortedByDescending {
                when (searchAttributes.filter) {
                    SearchAttributes.Filter.NAME -> it.fullName.lastName
                    SearchAttributes.Filter.MEDICARE_NUMBER -> it.medicareNumber.number
                }
            }
        }
    }

    override suspend fun insertClient(client: Client): Client {
        return appDatabase.insertClient(client)
    }

    override fun insertReferral(requisition: Requisition, clientId: Int, medicareNumber: MedicareNumber) {
        appDatabase.insertRequisition(requisition, clientId, medicareNumber)
    }

    override fun getClient(clientId: Int): Client? {
        return appDatabase.getClient(clientId)
    }

    override fun getClientsForReport(
        from: DateTime,
        to: DateTime,
        filter: SearchAttributes.Filter,
        order: SearchAttributes.Order
    ): List<Client> {
        val clients = appDatabase.getClients().filter { client ->
            client.requisitions.any { referral ->
                referral.createdOn.isAfter(from) && referral.createdOn.isBefore(to)
            }
        }

        return when (order) {
            SearchAttributes.Order.ASCENDING -> clients.sortedBy {
                when (filter) {
                    SearchAttributes.Filter.NAME -> it.fullName.lastName
                    SearchAttributes.Filter.MEDICARE_NUMBER -> it.medicareNumber.number
                }
            }
            SearchAttributes.Order.DESCENDING -> clients.sortedByDescending {
                when (filter) {
                    SearchAttributes.Filter.NAME -> it.fullName.lastName
                    SearchAttributes.Filter.MEDICARE_NUMBER -> it.medicareNumber.number
                }
            }
        }
    }

    override suspend fun updateClientQuickbooksId(clientId: Int, quickbooksId: Int) {
        appDatabase.updateClientQuickbooksId(clientId, quickbooksId)
    }

    override fun updateRequisitions(clientId: Int, requisition: Requisition) {
        appDatabase.updateClientRequisitions(clientId, requisition)
    }

    override fun deleteRequisition(requisition: Requisition) {
        appDatabase.deleteRequisition(requisition)
    }

    override fun addRequisition(clientId: Int, requisition: Requisition, medicareNumber: MedicareNumber) {
        appDatabase.addRequisition(clientId, requisition, medicareNumber)
    }
}