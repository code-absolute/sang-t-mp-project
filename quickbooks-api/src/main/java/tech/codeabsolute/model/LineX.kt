package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class LineX(
    @SerializedName("Amount")
    val amount: Double = 0.0,
    @SerializedName("DetailType")
    val detailType: String = "",
    @SerializedName("Id")
    val id: String = "",
    @SerializedName("LineNum")
    val lineNum: Int = 0,
    @SerializedName("SalesItemLineDetail")
    val salesItemLineDetail: SalesItemLineDetail = SalesItemLineDetail(),
    @SerializedName("SubTotalLineDetail")
    val subTotalLineDetail: SubTotalLineDetail = SubTotalLineDetail()
)