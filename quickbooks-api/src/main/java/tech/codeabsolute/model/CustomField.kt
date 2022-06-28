package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class CustomField(
    @SerializedName("DefinitionId")
    val definitionId: String = "",
    @SerializedName("Name")
    val name: String = "",
    @SerializedName("Type")
    val type: String = ""
)