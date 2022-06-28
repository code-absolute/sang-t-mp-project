package tech.codeabsolute.presentation.resources_provider

interface ValidationResourcesProvider {
    val errorMessageBlankFirstName: String
    val errorMessageBlankLastName: String
    val errorMessageBlankDateOfBirth: String
    val errorMessageBlankYear: String
    val errorMessageNotAValidYear: String
    val errorMessageBlankMonth: String
    val errorMessageNotAValidMonth: String
    val errorMessageBlankDay: String
    val errorMessageNotAValidDay: String
    val errorMessageBlankMedicareNumber: String
    val errorMessageNotAValidMedicareNumber: String
    val errorMessageBlankPhoneNumber: String
    val errorMessageNotAValidPhoneNumber: String
    val errorMessageBlankEmail: String
    val errorMessageNotAValidEmail: String
    val errorMessageBlankAddressNumber: String
    val errorMessageBlankAddressStreet: String
    val errorMessageBlankAddressCity: String
    val errorMessageBlankAddressProvince: String
    val errorMessageBlankAddressPostalCode: String
    val errorMessageNotAValidPostalCode: String
}