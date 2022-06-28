package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single

@Single
data class ValidationUseCases(
    val validFirstNameUseCase: ValidFirstNameUseCase,
    val validLastNameUseCase: ValidLastNameUseCase,
    val validDateOfBirthUseCase: ValidDateOfBirthUseCase,
    val validBirthYearUseCase: ValidBirthYearUseCase,
    val validBirthMonthUseCase: ValidBirthMonthUseCase,
    val validBirthDayUseCase: ValidBirthDayUseCase,
    val validMedicareNumberUseCase: ValidMedicareNumberUseCase,
    val validPhoneNumberUseCase: ValidPhoneNumberUseCase,
    val validPhoneNumberOtherUseCase: ValidPhoneNumberOtherUseCase,
    val validEmailUseCase: ValidEmailUseCase,
    val validAddressNumberUseCase: ValidAddressNumberUseCase,
    val validAddressStreetUseCase: ValidAddressStreetUseCase,
    val validAddressCityUseCase: ValidAddressCityUseCase,
    val validAddressProvinceUseCase: ValidAddressProvinceUseCase,
    val validAddressPostalCodeUseCase: ValidAddressPostalCodeUseCase,
)