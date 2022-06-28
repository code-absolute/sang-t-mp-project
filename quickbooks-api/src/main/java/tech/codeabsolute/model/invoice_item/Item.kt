package tech.codeabsolute.model.invoice_item


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("Active")
    val active: Boolean = false,
    @SerializedName("domain")
    val domain: String = "",
    @SerializedName("FullyQualifiedName")
    val fullyQualifiedName: String = "",
    @SerializedName("Id")
    val id: String = "",
    @SerializedName("Level")
    val level: Int = 0,
    @SerializedName("MetaData")
    val metaData: MetaData = MetaData(),
    @SerializedName("Name")
    val name: String = "",
    @SerializedName("ParentRef")
    val parentRef: ParentRef = ParentRef(),
    @SerializedName("sparse")
    val sparse: Boolean = false,
    @SerializedName("SubItem")
    val subItem: Boolean = false,
    @SerializedName("SyncToken")
    val syncToken: String = "",
    @SerializedName("Type")
    val type: String = ""
)