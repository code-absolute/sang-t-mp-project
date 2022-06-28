package tech.codeabsolute.model

import com.google.gson.annotations.SerializedName

data class QuickbooksCustomerResponse(
    @SerializedName("Customer")
    val customer: Customer = Customer(),
    @SerializedName("time")
    val time: String = ""
)
