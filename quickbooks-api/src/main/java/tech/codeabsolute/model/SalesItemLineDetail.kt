package tech.codeabsolute.model
import com.google.gson.annotations.SerializedName

data class SalesItemLineDetail(
    @SerializedName("ItemRef")
    val itemRef: ItemRef = ItemRef()
)