package tech.codeabsolute.model

import com.google.gson.annotations.SerializedName

data class QuickbooksCustomer(
    @SerializedName("BillAddr")
    val billAddr: BillAddr = BillAddr(),
    @SerializedName("CompanyName")
    val companyName: String = "",
    @SerializedName("DisplayName")
    val displayName: String = "",
    @SerializedName("FamilyName")
    val familyName: String = "",
    @SerializedName("FullyQualifiedName")
    val fullyQualifiedName: String = "",
    @SerializedName("GivenName")
    val givenName: String = "",
    @SerializedName("MiddleName")
    val middleName: String = "",
    @SerializedName("Notes")
    val notes: String = "",
    @SerializedName("PrimaryEmailAddr")
    val primaryEmailAddr: PrimaryEmailAddr = PrimaryEmailAddr(),
    @SerializedName("PrimaryPhone")
    val primaryPhone: PrimaryPhone = PrimaryPhone(),
    @SerializedName("Suffix")
    val suffix: String = "",
    @SerializedName("Title")
    val title: String = ""
)
