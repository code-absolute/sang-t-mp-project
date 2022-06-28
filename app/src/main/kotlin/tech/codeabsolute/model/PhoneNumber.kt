package tech.codeabsolute.domain.model

import tech.codeabsolute.util.empty

@JvmInline
value class PhoneNumber(val number: String = String.empty()) {
    override fun toString(): String = "${number.substring(0, 3)} ${number.substring(3, 6)} ${number.substring(6, 10)}"
}