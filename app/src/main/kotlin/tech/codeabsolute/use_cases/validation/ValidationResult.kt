package tech.codeabsolute.use_cases.validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
