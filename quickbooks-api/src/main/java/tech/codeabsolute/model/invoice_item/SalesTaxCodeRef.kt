package tech.codeabsolute.model.invoice_item

import com.google.gson.annotations.SerializedName
import tech.codeabsolute.util.empty

data class SalesTaxCodeRef(
    @SerializedName("value")
    var value: String = String.empty(),
    @SerializedName("name")
    var name: String = String.empty()
)
