package tech.codeabsolute.model

import tech.codeabsolute.util.empty

@JvmInline
value class EmailAddress(val email: String = String.empty()) {
    override fun toString(): String = email
}