package tech.codeabsolute.model
import com.google.gson.annotations.SerializedName

data class ItemRef(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("value")
    val value: String = ""
)