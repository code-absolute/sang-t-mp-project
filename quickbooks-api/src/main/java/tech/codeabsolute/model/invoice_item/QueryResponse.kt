package tech.codeabsolute.model.invoice_item


import com.google.gson.annotations.SerializedName

data class QueryResponse(
    @SerializedName("Item")
    val item: List<Item> = listOf(),
    @SerializedName("maxResults")
    val maxResults: Int = 0,
    @SerializedName("startPosition")
    val startPosition: Int = 0
)