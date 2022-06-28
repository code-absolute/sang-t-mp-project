package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase
import java.util.regex.Pattern

@Single
class ValidBirthMonth(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidBirthMonthUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageBlankMonth
        )
        !Pattern.matches(REGEX_PATTERN, input) -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageNotAValidMonth
        )
        else -> ValidationResult(
            successful = true
        )
    }

    companion object {
        const val REGEX_PATTERN = "^(0?[1-9]|1[012])\$"
    }
}

interface ValidBirthMonthUseCase : UseCase.WithInputAndOutput<String, ValidationResult>