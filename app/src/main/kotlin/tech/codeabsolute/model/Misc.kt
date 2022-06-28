package tech.codeabsolute.domain.model

import org.joda.time.DateTime

data class Misc(
    val path: String = "",
    val createdOn: DateTime = DateTime(),
    val type: MiscType
) {
    enum class MiscType {
        COVID_TEST,
        VACCINE,
    }
}
