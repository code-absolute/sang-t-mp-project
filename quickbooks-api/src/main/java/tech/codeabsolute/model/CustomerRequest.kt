package tech.codeabsolute.model

import com.google.gson.annotations.SerializedName

data class CustomerRequest(
    @SerializedName("BillAddr")
    val billAddr: BillAddrRequest = BillAddrRequest(),
    @SerializedName("FamilyName")
    val familyName: String = "",
    @SerializedName("GivenName")
    val givenName: String = "",
    @SerializedName("PrimaryEmailAddr")
    val primaryEmailAddr: PrimaryEmailAddr = PrimaryEmailAddr(),
    @SerializedName("PrimaryPhone")
    val primaryPhone: PrimaryPhone = PrimaryPhone(),
)

data class BillAddrRequest(
    @SerializedName("City")
    val city: String = "",
    @SerializedName("Country")
    val country: String = "",
    @SerializedName("Line1")
    val line1: String = "",
    @SerializedName("PostalCode")
    val postalCode: String = ""
)