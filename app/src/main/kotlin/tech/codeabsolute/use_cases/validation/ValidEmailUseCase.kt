package tech.codeabsolute.use_cases.validation

import org.koin.core.annotation.Single
import tech.codeabsolute.presentation.resources_provider.ValidationResourcesProvider
import tech.codeabsolute.use_cases.UseCase
import java.util.regex.Pattern

@Single
class ValidEmail(
    private val resourcesProvider: ValidationResourcesProvider
) : ValidEmailUseCase {

    override fun invoke(input: String): ValidationResult = when {
        input.isBlank() -> ValidationResult(
            successful = true
        )
        !Pattern.matches(REGEX_PATTERN, input) -> ValidationResult(
            successful = false,
            errorMessage = resourcesProvider.errorMessageNotAValidEmail
        )
        else -> ValidationResult(
            successful = true
        )
    }

    companion object {
        const val REGEX_PATTERN = "^[\\w-.]+@[\\w-]+\\.+[\\w-]{2,4}\$"
    }
}

interface ValidEmailUseCase : UseCase.WithInputAndOutput<String, ValidationResult>