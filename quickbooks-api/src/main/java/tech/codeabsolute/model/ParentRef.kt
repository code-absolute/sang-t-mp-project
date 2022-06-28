package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class ParentRef(
    @SerializedName("value")
    val value: String = ""
)