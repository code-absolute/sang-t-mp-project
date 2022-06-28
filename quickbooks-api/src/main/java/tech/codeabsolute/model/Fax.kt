package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class Fax(
    @SerializedName("FreeFormNumber")
    val freeFormNumber: String = ""
)