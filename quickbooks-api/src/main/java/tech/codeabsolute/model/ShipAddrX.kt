package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class ShipAddrX(
    @SerializedName("City")
    val city: String = "",
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