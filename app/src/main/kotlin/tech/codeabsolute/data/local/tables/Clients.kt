package tech.codeabsolute.data.local.tables

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.CurrentDateTime
import org.jetbrains.exposed.sql.jodatime.datetime
import org.jetbrains.exposed.sql.statements.InsertStatement
import tech.codeabsolute.domain.model.FullName
import tech.codeabsolute.domain.model.PhoneNumber
import tech.codeabsolute.model.*

object Clients : Table("clients") {

    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    val quickbooksId = integer("quickbooks_id")
    val firstName = varchar("first_name", 120)
    val lastName = varchar("last_name", 120)
    val medicareNumber = varchar("medicare_number", 12)
    val dateOfBirth = datetime("date_of_birth")
    val phoneNumber = varchar("phone_number", 10)
    val otherNumber = varchar("other_number", 10)
    val email = varchar("email", 120)
    val addressNumber = varchar("address_number", 10).nullable()
    val addressStreet = varchar("address_street", 120).nullable()
    val addressCity = varchar("address_city", 120).nullable()
    val addressPostalCode = varchar("address_postal_code", 6).nullable()
    val addressApt = varchar("address_apartment", 6).nullable()
    val addressProvince = varchar("address_province", 2).nullable()
    val createdOn = datetime("created_on").defaultExpression(CurrentDateTime)
    val viewedOn = datetime("viewed_on").defaultExpression(CurrentDateTime)

    fun InsertStatement<Number>.insert(client: Client) {
        this[quickbooksId] = client.quickbooksId
        this[firstName] = client.fullName.firstName
        this[lastName] = client.fullName.lastName
        this[medicareNumber] = client.medicareNumber.number
        this[dateOfBirth] = client.dateOfBirth.toDateTime()
        this[phoneNumber] = client.contactInfo.phoneNumber.number
        this[otherNumber] = client.contactInfo.otherNumber.number
        this[email] = client.contactInfo.emailAddress.email
        this[addressNumber] = client.address?.number
        this[addressStreet] = client.address?.street
        this[addressCity] = client.address?.city
        this[addressPostalCode] = client.address?.postalCode
        this[addressApt] = client.address?.apartment
        this[addressProvince] = client.address?.province
        this[createdOn] = client.createdOn
        this[viewedOn] = client.viewedOn
    }

    fun ResultRow.toClient(referrals: List<Requisition>): Client {

        val address = if (this[addressNumber] != null) {
            Address(
                number = this[addressNumber] ?: "",
                street = this[addressStreet] ?: "",
                city = this[addressCity] ?: "",
                province = this[addressProvince] ?: "",
                postalCode = this[addressPostalCode] ?: ""
            )
        } else {
            null
        }

        return Client(
            id = this[id],
            quickbooksId = this[quickbooksId],
            fullName = FullName(
                firstName = this[firstName],
                lastName = this[lastName]
            ),
            dateOfBirth = DateOfBirth(
                this[dateOfBirth].dayOfMonth,
                this[dateOfBirth].monthOfYear,
                this[dateOfBirth].year
            ),
            medicareNumber = MedicareNumber(this[medicareNumber]),
            contactInfo = ContactInfo(
                phoneNumber = PhoneNumber(this[phoneNumber]),
                otherNumber = PhoneNumber(this[otherNumber]),
                emailAddress = EmailAddress(this[email])
            ),
            address = address,
            requisitions = referrals,
            createdOn = this[createdOn],
            viewedOn = this[viewedOn],
        )
    }
}
