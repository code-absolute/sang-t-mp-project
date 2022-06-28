package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase
import java.util.regex.Pattern

@Single
class ValidDateOfBirth(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidDateOfBirthUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageBlankDateOfBirth
        )
        !Pattern.matches(REGEX_PATTERN_YEAR, input.substring(0, 2)) -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageNotAValidYear
        )
        !Pattern.matches(REGEX_PATTERN_MONTH, input.substring(3, 5)) -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageNotAValidMonth
        )
        !Pattern.matches(REGEX_PATTERN_DAY, input.substring(6, 8)) -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageNotAValidDay
        )
        else -> ValidationResult(
            successful = true
        )
    }

    companion object {
        const val REGEX_PATTERN_YEAR = "^[0-9]{2}\$"
        const val REGEX_PATTERN_MONTH = "^(0?[1-9]|1[012])\$"
        const val REGEX_PATTERN_DAY = "^(0?[1-9]|[12]\\d|3[01])\$"
    }
}

interface ValidDateOfBirthUseCase : UseCase.WithInputAndOutput<String, ValidationResult>