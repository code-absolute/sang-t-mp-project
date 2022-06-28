package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase
import java.util.regex.Pattern

@Single
class ValidMedicareNumber(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidMedicareNumberUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageBlankMedicareNumber
        )
        !Pattern.matches(REGEX_PATTERN, input) -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageNotAValidMedicareNumber
        )
        else -> ValidationResult(
            successful = true
        )
    }

    companion object {
        const val REGEX_PATTERN = "^([A-Z]){4} ([0-9]){4} ([0-9]){4}\$"
    }
}

interface ValidMedicareNumberUseCase : UseCase.WithInputAndOutput<String, ValidationResult>