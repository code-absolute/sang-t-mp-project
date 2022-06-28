package tech.codeabsolute.data

import tech.codeabsolute.model.Customer
import tech.codeabsolute.model.CustomerRequest
import tech.codeabsolute.model.InvoiceRequest
import tech.codeabsolute.model.invoice_item.Item
import tech.codeabsolute.util.AppPreferences

class QuickbooksPublicDataSource(
    private val quickbooksService: QuickbooksService,
    private val appPreferences: AppPreferences
) : QuickbooksDataSource {

    override suspend fun createCustomer(customerRequest: CustomerRequest): Customer? {
        val response = quickbooksService.createCustomer(
            "Bearer ${appPreferences.getAccessToken()?.value}",
            appPreferences.getRealmId()?.value,
            customerRequest
        )
        return response.body()?.customer
    }

    override suspend fun queryCustomers(searchTerm: String): List<Customer>? {
        val response = quickbooksService.queryCustomers(
            "Bearer ${appPreferences.getAccessToken()?.value}",
            appPreferences.getRealmId()?.value,
            "select * from Customer Where Metadata.LastUpdatedTime > '2015-03-01'"
        )
        return response.body()?.queryResponse?.customer
    }

    override suspend fun getCustomer(givenName: String?, familyName: String?): Customer? {
        val query = "select * from Customer Where GivenName = '$givenName' AND FamilyName = '$familyName'"

        val response = quickbooksService.queryCustomer(
            "Bearer ${appPreferences.getAccessToken()?.value}",
            appPreferences.getRealmId()?.value,
            query
        )
        return response.body()?.queryResponse?.customer?.firstOrNull()
    }

    override suspend fun createInvoice(invoiceRequest: InvoiceRequest) {
        quickbooksService.createInvoice(
            "Bearer ${appPreferences.getAccessToken()?.value}",
            appPreferences.getRealmId()?.value,
            invoiceRequest
        )
    }

    override suspend fun getInvoiceItems(): List<Item> {
        val firstQuery = "select id from Item where Type='Category' && Name='Requisition'"
        val parentRefResponse = quickbooksService.getParentRef(
            "Bearer ${appPreferences.getAccessToken()?.value}",
            appPreferences.getRealmId()?.value,
            firstQuery
        )
        val parentRefId = parentRefResponse.body()?.queryResponse?.item?.firstOrNull()?.id

        val query = "select * from Item Where ParentRef='$parentRefId'"

        val response = quickbooksService.getInvoiceItems(
            "Bearer ${appPreferences.getAccessToken()?.value}",
            appPreferences.getRealmId()?.value,
            query
        )
        return response.body()?.queryResponse?.item ?: listOf()
    }
}