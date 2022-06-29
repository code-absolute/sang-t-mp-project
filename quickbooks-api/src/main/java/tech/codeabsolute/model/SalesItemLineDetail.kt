package tech.codeabsolute.model
import com.google.gson.annotations.SerializedName

data class SalesItemLineDetail(
    @SerializedName("ItemRef")
    val itemRef: ItemRef = ItemRef(),
    @SerializedName("TaxCodeRef")
    val taxCodeRef: TaxCodeRef = TaxCodeRef(),
    @SerializedName("Qty")
    val quantity: Int = 0
)
