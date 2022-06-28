package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class WebAddr(
    @SerializedName("URI")
    val uRI: String = ""
)