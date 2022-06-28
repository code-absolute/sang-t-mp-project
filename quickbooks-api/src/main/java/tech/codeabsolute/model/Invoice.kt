package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class Invoice(
    @SerializedName("ApplyTaxAfterDiscount")
    val applyTaxAfterDiscount: Boolean = false,
    @SerializedName("Balance")
    val balance: Double = 0.0,
    @SerializedName("BillAddr")
    val billAddr: BillAddr = BillAddr(),
    @SerializedName("CustomField")
    val customField: List<CustomField> = listOf(),
    @SerializedName("CustomerRef")
    val customerRef: CustomerRef = CustomerRef(),
    @SerializedName("Deposit")
    val deposit: Int = 0,
    @SerializedName("DocNumber")
    val docNumber: String = "",
    @SerializedName("domain")
    val domain: String = "",
    @SerializedName("DueDate")
    val dueDate: String = "",
    @SerializedName("EmailStatus")
    val emailStatus: String = "",
    @SerializedName("Id")
    val id: String = "",
    @SerializedName("Line")
    val line: List<Line> = listOf(),
    @SerializedName("LinkedTxn")
    val linkedTxn: List<Any> = listOf(),
    @SerializedName("MetaData")
    val metaData: MetaData = MetaData(),
    @SerializedName("PrintStatus")
    val printStatus: String = "",
    @SerializedName("ShipAddr")
    val shipAddr: ShipAddr = ShipAddr(),
    @SerializedName("sparse")
    val sparse: Boolean = false,
    @SerializedName("SyncToken")
    val syncToken: String = "",
    @SerializedName("TotalAmt")
    val totalAmt: Double = 0.0,
    @SerializedName("TxnDate")
    val txnDate: String = "",
    @SerializedName("TxnTaxDetail")
    val txnTaxDetail: TxnTaxDetail = TxnTaxDetail()
)