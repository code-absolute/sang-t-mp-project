package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class QuickbooksQueryResponse(
    @SerializedName("QueryResponse")
    val queryResponse: QueryResponse = QueryResponse(),
    @SerializedName("time")
    val time: String = ""
)