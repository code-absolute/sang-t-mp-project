package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase
import java.util.regex.Pattern

@Single
class ValidAddressPostalCode(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidAddressPostalCodeUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageBlankAddressPostalCode
        )
        !Pattern.matches(REGEX_PATTERN, input) -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageNotAValidPostalCode
        )
        else -> ValidationResult(
            successful = true
        )
    }

    companion object {
        const val REGEX_PATTERN = "^[A-Z][0-9][A-Z] [0-9][A-Z][0-9]\$"
    }
}

interface ValidAddressPostalCodeUseCase : UseCase.WithInputAndOutput<String, ValidationResult>