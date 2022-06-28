package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase
import java.util.regex.Pattern

@Single
class ValidPhoneNumber(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidPhoneNumberUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageBlankPhoneNumber
        )
        !Pattern.matches(REGEX_PATTERN, input) -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageNotAValidPhoneNumber
        )
        else -> ValidationResult(
            successful = true
        )
    }

    companion object {
        const val REGEX_PATTERN = "^[0-9]{3} [0-9]{3} [0-9]{4}\$"
    }
}

interface ValidPhoneNumberUseCase : UseCase.WithInputAndOutput<String, ValidationResult>