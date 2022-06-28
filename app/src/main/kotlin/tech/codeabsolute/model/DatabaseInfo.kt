package tech.codeabsolute.model

data class DatabaseInfo(
    val name: String,
    val directory: String,
    val url: String,
    val driver: String,
    val deletePreviousData: Boolean = false
)
