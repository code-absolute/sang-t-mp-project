package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class MetaDataX(
    @SerializedName("CreateTime")
    val createTime: String = "",
    @SerializedName("LastUpdatedTime")
    val lastUpdatedTime: String = ""
)