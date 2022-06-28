package tech.codeabsolute.presentation.new_client

import tech.codeabsolute.model.Requisition
import tech.codeabsolute.model.invoice_item.Item
import tech.codeabsolute.util.empty

data class NewClientSectionState(
    val firstName: String = String.empty(),
    val firstNameError: String? = null,
    val lastName: String = String.empty(),
    val lastNameError: String? = null,
    val dateOfBirth: String = String.empty(),
    val dateOfBirthError: String? = null,
    val medicareNumber: String = String.empty(),
    val medicareNumberError: String? = null,
    val phoneNumber: String = String.empty(),
    val phoneNumberError: String? = null,
    val phoneNumberOther: String = String.empty(),
    val phoneNumberOtherError: String? = null,
    val email: String = String.empty(),
    val emailError: String? = null,
    val addressChecked: Boolean = false,
    val addressNumber: String = String.empty(),
    val addressNumberError: String? = null,
    val addressStreet: String = String.empty(),
    val addressStreetError: String? = null,
    val addressApt: String = String.empty(),
    val addressCity: String = String.empty(),
    val addressCityError: String? = null,
    val addressProvince: String = "QC",
    val addressProvinceError: String? = null,
    val addressPostalCode: String = String.empty(),
    val addressPostalCodeError: String? = null,
    val requisitions: List<Requisition> = listOf(),
    val requisitionTypes: List<Item> = listOf(),
    val isLoading: Boolean = false,
    val hasErrors: Boolean = false
)

