package tech.codeabsolute.domain.model

import tech.codeabsolute.util.empty

data class FullName(
    val firstName: String = String.empty(),
    val lastName: String = String.empty()
) {
    override fun toString(): String = "$lastName, $firstName"
}
