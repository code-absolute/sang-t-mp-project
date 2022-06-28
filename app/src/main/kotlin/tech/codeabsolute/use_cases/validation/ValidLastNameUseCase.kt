package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase

@Single
class ValidLastName(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidLastNameUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageBlankLastName
        )
        else -> ValidationResult(
            successful = true
        )
    }
}

interface ValidLastNameUseCase : UseCase.WithInputAndOutput<String, ValidationResult>