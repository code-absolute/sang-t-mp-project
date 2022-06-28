package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase

@Single
class ValidFirstName(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidFirstNameUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageBlankFirstName
        )
        else -> ValidationResult(
            successful = true
        )
    }
}

interface ValidFirstNameUseCase : UseCase.WithInputAndOutput<String, ValidationResult>