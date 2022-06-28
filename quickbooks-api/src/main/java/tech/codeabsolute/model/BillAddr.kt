package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class BillAddr(
    @SerializedName("City")
    val city: String = "",
    @SerializedName("Country")
    val country: String = "",
    @SerializedName("CountrySubDivisionCode")
    val countrySubDivisionCode: String = "",
    @SerializedName("Id")
    val id: String = "",
    @SerializedName("Lat")
    val lat: String = "",
    @SerializedName("Line1")
    val line1: String = "",
    @SerializedName("Long")
    val long: String = "",
    @SerializedName("PostalCode")
    val postalCode: String = ""
)