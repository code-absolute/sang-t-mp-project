package tech.codeabsolute.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.codeabsolute.data.QuickbooksDataSource
import tech.codeabsolute.data.local.dao.ClientsDao
import tech.codeabsolute.domain.model.*
import tech.codeabsolute.model.*
import tech.codeabsolute.model.invoice_item.Item
import tech.codeabsolute.util.Resource

class ClientsRepositoryImpl(
    private val clientsDao: ClientsDao,
    private val quickbooksDataSource: QuickbooksDataSource
) : ClientsRepository {

    override fun getClient(clientId: Int?): Flow<Resource<Client>> = flow {
        emit(Resource.Loading())
        try {
            clientId?.let {
                val client = clientsDao.getClient(it)
                emit(Resource.Success(client))
            } ?: emit(Resource.Error("Client Id is null"))
        } catch (e: Exception) {
            emit(Resource.Error("${e.message}"))
        }
    }

    override fun getClients(searchAttributes: SearchAttributes): Flow<Resource<List<Client>>> = flow {
        emit(Resource.Loading())
        try {

//            val clients = if (searchAttributes.searchTerm.isBlank()) {
//                emptyList()
//            } else {
//                quickbooksDataSource.queryCustomers(searchAttributes.searchTerm)?.mapNotNull { customer ->
//                    clientsDao.getClient(customer.id.toInt())?.let {
//                        customer.toClient(it)
//                    }
//                } ?: emptyList()
//            }

            val clients = if (searchAttributes.searchTerm.isBlank()) {
                emptyList()
            } else {
                clientsDao.getClients(searchAttributes)
            }
            emit(Resource.Success(data = clients))
        } catch (e: Exception) {
            emit(Resource.Error("${e.message}"))
        }
    }

    override fun createClient(client: Client): Flow<Resource<Client>> = flow {
        emit(Resource.Loading())
        try {
            val insertedClient = clientsDao.insertClient(client)
            createQuickbooksCustomer(insertedClient)
            emit(Resource.Success(insertedClient))
        } catch (e: Exception) {
            emit(Resource.Error("${e.message}"))
        }
    }

    private suspend fun createQuickbooksCustomer(client: Client) {
        try {
            val billAddrRequest = if (client.address != null) {
                BillAddrRequest(
                    city = client.address.city,
                    country = "Canada",
                    line1 = "${client.address.number} ${client.address.street}",
                    postalCode = client.address.postalCode
                )
            } else {
                BillAddrRequest()
            }

            val customer = quickbooksDataSource.createCustomer(
                CustomerRequest(
                    familyName = client.fullName.lastName,
                    givenName = client.fullName.firstName,
                    billAddr = billAddrRequest,
                    primaryEmailAddr = PrimaryEmailAddr(address = client.contactInfo.emailAddress.email),
                    primaryPhone = PrimaryPhone(freeFormNumber = client.contactInfo.phoneNumber.number)
                )
            )

            if (customer != null) {
                clientsDao.updateClientQuickbooksId(client.id, customer.id.toInt())
            }
        } catch (e: Exception) {
            println("ClientsRepository ${e.message}")
        }
    }

    override suspend fun getRequisitionTypes(): List<Item> {
        return quickbooksDataSource.getInvoiceItems()
    }

    override fun createInvoice(quickbooksClientId: Int, requisition: Requisition): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val types = getRequisitionTypes()

        val item = types.first { it.id == requisition.typeId }

        val invoiceRequest = InvoiceRequest(
            CustomerRef(quickbooksClientId.toString()),
            listOf(
                Line(
                    detailType = "SalesItemLineDetail",
                    salesItemLineDetail = SalesItemLineDetail(
                        ItemRef(
                            item.name,
                            item.id
                        ),
                        TaxCodeRef(
                            item.salesTaxCodeRef.value
                        ),
                        quantity = 1,
                    ),
                    amount = item.unitPrice,
                    description = item.description
                )
            )
        )
        quickbooksDataSource.createInvoice(invoiceRequest)
    }

    override fun updateRequisitions(clientId: Int, requisition: Requisition) {
        clientsDao.updateRequisitions(clientId, requisition)
    }

    private fun Customer.toClient(client: Client) = Client(
        fullName = FullName(
            firstName = givenName,
            lastName = familyName
        ),
        dateOfBirth = client.dateOfBirth,
        medicareNumber = client.medicareNumber,
        contactInfo = ContactInfo(
            phoneNumber = PhoneNumber(primaryPhone.freeFormNumber),
            emailAddress = EmailAddress(primaryEmailAddr.address)
        ),
        address = Address(
            number = "-1",
            street = billAddr.line1,
            city = billAddr.city,
            postalCode = billAddr.postalCode
        ),
        requisitions = client.requisitions
    )
}