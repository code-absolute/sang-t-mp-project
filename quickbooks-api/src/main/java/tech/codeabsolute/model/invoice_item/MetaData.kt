package tech.codeabsolute.model.invoice_item


import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("CreateTime")
    val createTime: String = "",
    @SerializedName("LastUpdatedTime")
    val lastUpdatedTime: String = ""
)