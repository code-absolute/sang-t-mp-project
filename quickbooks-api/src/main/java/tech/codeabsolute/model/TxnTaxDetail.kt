package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class TxnTaxDetail(
    @SerializedName("TotalTax")
    val totalTax: Int = 0
)