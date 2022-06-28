package tech.codeabsolute.presentation.resources_provider

import org.koin.core.annotation.Single

@Single
class EnglishValidationResourcesProvider : ValidationResourcesProvider {
    override val errorMessageBlankFirstName: String = "First name can't be blank"
    override val errorMessageBlankLastName: String = "Last name can't be blank"
    override val errorMessageBlankDateOfBirth: String = "Date of birth can't be blank"
    override val errorMessageBlankYear: String = "Year of birth can't be blank"
    override val errorMessageNotAValidYear: String = "Year of birth is not valid"
    override val errorMessageBlankMonth: String = "Month of birth can't be blank"
    override val errorMessageNotAValidMonth: String = "Month of birth is not valid"
    override val errorMessageBlankDay: String = "Day of birth can't be blank"
    override val errorMessageNotAValidDay: String = "Day of birth is not valid"
    override val errorMessageBlankMedicareNumber: String = "Medicare number can't be blank"
    override val errorMessageNotAValidMedicareNumber: String = "Medicare number is not valid"
    override val errorMessageBlankPhoneNumber: String = "Phone number can't be blank"
    override val errorMessageNotAValidPhoneNumber: String = "Phone number is not valid"
    override val errorMessageBlankEmail: String = "Email can't be blank"
    override val errorMessageNotAValidEmail: String = "Email is not valid"
    override val errorMessageBlankAddressNumber: String = "Number can't be blank"
    override val errorMessageBlankAddressStreet: String = "Street can't be blank"
    override val errorMessageBlankAddressCity: String = "City can't be blank"
    override val errorMessageBlankAddressProvince: String = "Province can't be blank"
    override val errorMessageBlankAddressPostalCode: String = "Postal code can't be blank"
    override val errorMessageNotAValidPostalCode: String = "Postal code is not valid"
}