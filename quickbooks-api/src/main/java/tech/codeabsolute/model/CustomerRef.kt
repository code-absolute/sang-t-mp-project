package tech.codeabsolute.model
import com.google.gson.annotations.SerializedName

data class CustomerRef(
    @SerializedName("value")
    val value: String = ""
)