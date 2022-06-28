package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class QueryResponse(
    @SerializedName("Customer")
    val customer: List<Customer> = listOf(),
    @SerializedName("maxResults")
    val maxResults: Int = 0,
    @SerializedName("startPosition")
    val startPosition: Int = 0
)