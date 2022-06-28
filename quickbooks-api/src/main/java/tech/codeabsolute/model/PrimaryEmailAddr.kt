package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class PrimaryEmailAddr(
    @SerializedName("Address")
    val address: String = ""
)