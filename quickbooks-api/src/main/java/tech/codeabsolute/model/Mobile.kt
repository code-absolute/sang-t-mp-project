package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class Mobile(
    @SerializedName("FreeFormNumber")
    val freeFormNumber: String = ""
)