package tech.codeabsolute.data.local

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import tech.codeabsolute.data.local.tables.Clients
import tech.codeabsolute.data.local.tables.Requisitions
import tech.codeabsolute.data.local.tables.Accounts

object SchemaDefinition {

    fun createSchema() {
        transaction {
            SchemaUtils.create(Accounts)
            SchemaUtils.create(Clients)
            SchemaUtils.create(Requisitions)
        }
    }
}
