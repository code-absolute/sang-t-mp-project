package tech.codeabsolute.model.invoice_item


import com.google.gson.annotations.SerializedName

data class QuickbooksInvoiceItemsQueryResponse(
    @SerializedName("QueryResponse")
    val queryResponse: QueryResponse = QueryResponse(),
    @SerializedName("time")
    val time: String = ""
)