package tech.codeabsolute.model

import org.joda.time.DateTime
import tech.codeabsolute.domain.model.*

data class Client(
    val id: Int = -1,
    val quickbooksId: Int = -1,
    val fullName: FullName,
    val dateOfBirth: DateOfBirth,
    val medicareNumber: MedicareNumber,
    val contactInfo: ContactInfo,
    val address: Address? = null,
    val requisitions: List<Requisition>,
    val createdOn: DateTime = DateTime(),
    val viewedOn: DateTime = DateTime()
)