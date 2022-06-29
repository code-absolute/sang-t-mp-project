package tech.codeabsolute.model
import com.google.gson.annotations.SerializedName

data class Line(
    @SerializedName("Amount")
    val amount: Double = 0.0,
    @SerializedName("DetailType")
    val detailType: String = "",
    @SerializedName("SalesItemLineDetail")
    val salesItemLineDetail: SalesItemLineDetail = SalesItemLineDetail(),
    @SerializedName("Description")
    val description: String = ""
)