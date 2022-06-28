package tech.codeabsolute.model

import tech.codeabsolute.domain.model.PhoneNumber
import tech.codeabsolute.util.empty

data class ContactInfo(
    val phoneNumber: PhoneNumber,
    val otherNumber: PhoneNumber = PhoneNumber(String.empty()),
    val emailAddress: EmailAddress = EmailAddress(String.empty())
)
