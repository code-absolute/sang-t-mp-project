package tech.codeabsolute.model

import tech.codeabsolute.util.empty

data class Address(
    val number: String = String.empty(),
    val street: String = String.empty(),
    val city: String = String.empty(),
    val postalCode: String = String.empty(),
    val apartment: String = String.empty(),
    val province: String = "QC"
) {
    override fun toString(): String {

        val apt = if (apartment.isNotEmpty()) {
            "APT # $apartment"
        } else {
            String.empty()
        }

        return "$number $street $apt\n$city $province $postalCode"
    }
}
