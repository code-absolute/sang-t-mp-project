package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase

@Single
class ValidAddressCity(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidAddressCityUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageBlankAddressCity
        )
        else -> ValidationResult(
            successful = true
        )
    }
}

interface ValidAddressCityUseCase : UseCase.WithInputAndOutput<String, ValidationResult>