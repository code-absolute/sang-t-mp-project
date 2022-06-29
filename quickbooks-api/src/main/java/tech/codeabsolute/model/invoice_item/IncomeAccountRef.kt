package tech.codeabsolute.model.invoice_item

import com.google.gson.annotations.SerializedName

data class IncomeAccountRef(
    @SerializedName("value")
    var value: String? = null,
    @SerializedName("name")
    var name: String? = null
)
