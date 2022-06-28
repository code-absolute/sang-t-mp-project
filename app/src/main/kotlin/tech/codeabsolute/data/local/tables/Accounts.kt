package tech.codeabsolute.data.local.tables

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.CurrentDateTime
import org.jetbrains.exposed.sql.jodatime.datetime
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import tech.codeabsolute.model.Account

object Accounts : Table("accounts") {

    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    val name = varchar("name", 50)
    val username = varchar("username", 30)
    val password = varchar("password", 40)
    val createdOn = datetime("created_on").defaultExpression(CurrentDateTime)

    fun UpdateBuilder<*>.insert(account: Account) {
        this[name] = account.name
        this[username] = account.username
        this[password] = account.password
    }

    fun ResultRow.toAccount() = Account(
        name = this[name],
        username = this[username],
        password = this[password]
    )
}