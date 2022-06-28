package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class SalesItemLineDetailX(
    @SerializedName("ItemRef")
    val itemRef: ItemRef = ItemRef(),
    @SerializedName("TaxCodeRef")
    val taxCodeRef: TaxCodeRef = TaxCodeRef()
)