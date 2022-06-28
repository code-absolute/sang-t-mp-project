package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase

@Single
class ValidAddressStreet(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidAddressStreetUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageBlankAddressStreet
        )
        else -> ValidationResult(
            successful = true
        )
    }
}

interface ValidAddressStreetUseCase : UseCase.WithInputAndOutput<String, ValidationResult>