package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.model.Address
import tech.codeabsolute.use_cases.UseCase

@Single
class ValidAddress(
    val validAddressNumberUseCase: ValidAddressNumberUseCase,
    val validAddressStreetUseCase: ValidAddressStreetUseCase,
    val validAddressCityUseCase: ValidAddressCityUseCase,
    val validAddressProvinceUseCase: ValidAddressProvinceUseCase,
    val validAddressPostalCodeUseCase: ValidAddressPostalCodeUseCase
) : ValidAddressUseCase {

    override fun invoke(input: Address?): ValidationResult {
        if (input == null) {
            return ValidationResult(successful = true)
        } else {
            with(validAddressNumberUseCase(input.number)) {
                if (!successful) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = errorMessage
                    )
                }
            }

            with(validAddressStreetUseCase(input.street)) {
                if (!successful) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = errorMessage
                    )
                }
            }

            with(validAddressCityUseCase(input.city)) {
                if (!successful) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = errorMessage
                    )
                }
            }

            with(validAddressProvinceUseCase(input.province)) {
                if (!successful) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = errorMessage
                    )
                }
            }

            with(validAddressPostalCodeUseCase(input.postalCode)) {
                if (!successful) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = errorMessage
                    )
                }
            }

            return ValidationResult(successful = true)
        }
    }
}

interface ValidAddressUseCase : UseCase.WithInputAndOutput<Address?, ValidationResult>