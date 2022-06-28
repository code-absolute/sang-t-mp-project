package tech.codeabsolute.model

import org.joda.time.DateTime
import java.io.File

data class Requisition(
    val id: Int = -1,
    val path: String,
    val typeId: String,
    val typeName: String,
    val createdOn: DateTime = DateTime(),
    val testedOn: DateTime? = null
) {
    fun toFile() = File(path)
}

