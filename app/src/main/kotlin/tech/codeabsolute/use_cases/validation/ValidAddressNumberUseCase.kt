package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase

@Single
class ValidAddressNumber(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidAddressNumberUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageBlankAddressNumber
        )
        else -> ValidationResult(
            successful = true
        )
    }
}

interface ValidAddressNumberUseCase : UseCase.WithInputAndOutput<String, ValidationResult>