package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase

@Single
class ValidAddressProvince(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidAddressProvinceUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageBlankAddressProvince
        )
        else -> ValidationResult(
            successful = true
        )
    }
}

interface ValidAddressProvinceUseCase : UseCase.WithInputAndOutput<String, ValidationResult>