package tech.codeabsolute.repository

import tech.codeabsolute.data.QuickbooksDataSource
import tech.codeabsolute.model.*

class InvoicesRepository(
    private val quickbooksDataSource: QuickbooksDataSource
) {

    suspend fun createInvoice(customer: Customer) {
        quickbooksDataSource.createInvoice(
            InvoiceRequest(
                CustomerRef(customer.id),
                listOf(
                    Line(
                        40.0,
                        "SalesItemLineDetail",
                        SalesItemLineDetail(
                            ItemRef(
                                "Services",
                                "1"
                            )
                        )
                    )
                )
            )
        )
    }
}