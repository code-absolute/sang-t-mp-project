package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class PrimaryPhone(
    @SerializedName("FreeFormNumber")
    val freeFormNumber: String = ""
)