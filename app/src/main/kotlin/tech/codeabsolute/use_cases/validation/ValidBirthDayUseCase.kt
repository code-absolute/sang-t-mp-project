package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase
import java.util.regex.Pattern

@Single
class ValidBirthDay(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidBirthDayUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageBlankDay
        )
        !Pattern.matches(REGEX_PATTERN, input) -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageNotAValidDay
        )
        else -> ValidationResult(
            successful = true
        )
    }

    companion object {
        const val REGEX_PATTERN = "^(0?[1-9]|[12]\\d|3[01])\$"
    }
}

interface ValidBirthDayUseCase : UseCase.WithInputAndOutput<String, ValidationResult>