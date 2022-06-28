package tech.codeabsolute.model.invoice_item


import com.google.gson.annotations.SerializedName

data class ParentRef(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("value")
    val value: String = ""
)