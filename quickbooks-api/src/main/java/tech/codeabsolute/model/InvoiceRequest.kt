package tech.codeabsolute.model
import com.google.gson.annotations.SerializedName

data class InvoiceRequest(
    @SerializedName("CustomerRef")
    val customerRef: CustomerRef = CustomerRef(),
    @SerializedName("Line")
    val line: List<Line> = listOf()
)