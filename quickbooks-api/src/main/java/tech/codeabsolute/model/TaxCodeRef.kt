package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class TaxCodeRef(
    @SerializedName("value")
    val value: String = ""
)