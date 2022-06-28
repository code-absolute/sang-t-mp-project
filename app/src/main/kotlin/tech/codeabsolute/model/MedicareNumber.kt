package tech.codeabsolute.model

import tech.codeabsolute.util.empty

@JvmInline
value class MedicareNumber(val number: String = String.empty()) {
    override fun toString(): String =
        "${number.substring(0, 4)} ${number.substring(4, 8)} ${number.substring(8, 12)}".uppercase()
}