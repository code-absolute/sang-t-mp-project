package tech.codeabsolute.data.local.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.CurrentDateTime
import org.jetbrains.exposed.sql.jodatime.datetime
import org.jetbrains.exposed.sql.statements.InsertStatement
import tech.codeabsolute.model.Requisition

object Requisitions : Table("requisitions") {

    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    val patientId = integer("patient_id").references(Clients.id, onDelete = ReferenceOption.CASCADE)
    val path = varchar("path", 250)
    val typeId = varchar("type_id", 120)
    val typeName = varchar("type_name", 250)
    val createdOn = datetime("created_on").defaultExpression(CurrentDateTime)
    val testedOn = datetime("tested_on").nullable().default(null)

    fun InsertStatement<Number>.insert(requisition: Requisition, insertedPatientId: Int) {
        this[patientId] = insertedPatientId
        this[path] = requisition.path
        this[typeId] = requisition.typeId
        this[typeName] = requisition.typeName
        this[createdOn] = requisition.createdOn
        this[testedOn] = requisition.testedOn
    }

    fun ResultRow.toRequisition() = Requisition(
        id = this[id],
        path = this[path],
        typeId = this[typeId],
        typeName = this[typeName],
        createdOn = this[createdOn],
        testedOn = this[testedOn]
    )
}