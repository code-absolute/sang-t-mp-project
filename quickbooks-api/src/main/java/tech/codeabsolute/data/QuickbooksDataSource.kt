package tech.codeabsolute.data

import tech.codeabsolute.model.Customer
import tech.codeabsolute.model.CustomerRequest
import tech.codeabsolute.model.InvoiceRequest
import tech.codeabsolute.model.invoice_item.Item

interface QuickbooksDataSource {
    suspend fun createCustomer(customerRequest: CustomerRequest): Customer?
    suspend fun queryCustomers(searchTerm: String): List<Customer>?
    suspend fun getCustomer(givenName: String?, familyName: String?): Customer?
    suspend fun createInvoice(invoiceRequest: InvoiceRequest)
    suspend fun getInvoiceItems(): List<Item>
}