package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class QuickbooksInvoiceResponse(
    @SerializedName("Invoice")
    val invoice: Invoice = Invoice(),
    @SerializedName("time")
    val time: String = ""
)