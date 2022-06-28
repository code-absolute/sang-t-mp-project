package tech.codeabsolute.presentation.client_details

import tech.codeabsolute.model.Client

data class ClientDetailsSectionState(
    val client: Client? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
