package tech.codeabsolute.data.local

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import tech.codeabsolute.data.local.tables.Accounts
import tech.codeabsolute.data.local.tables.Accounts.toAccount
import tech.codeabsolute.data.local.tables.Clients
import tech.codeabsolute.data.local.tables.Clients.toClient
import tech.codeabsolute.data.local.tables.Requisitions
import tech.codeabsolute.data.local.tables.Requisitions.toRequisition
import tech.codeabsolute.encryption.DataEncryption
import tech.codeabsolute.model.*
import tech.codeabsolute.util.Resource
import java.io.File

class AppDatabaseImpl(
    private val dataEncryption: DataEncryption
) : AppDatabase {

    private val userHomeDirectory = System.getProperty("user.home")
    private val name = "sang_t_mp_db"
    private val directory = "$userHomeDirectory/Sang-T MP/"

    private val databaseInfo = DatabaseInfo(
        name = name,
        directory = directory,
        url = "jdbc:h2:$directory$name;MODE=MYSQL",
        driver = "org.h2.Driver",
        deletePreviousData = true
    )

    override suspend fun build(): Resource<Unit> {
        connect()
        insertAccount(
            Account(
                name = "David Winnicki",
                username = dataEncryption.encrypt("david"),
                password = dataEncryption.encrypt("12345")
            )
        )
        insertAccount(
            Account(
                name = "Marta Pilarska",
                username = dataEncryption.encrypt("marta"),
                password = dataEncryption.encrypt("s4ngr0ug3")
            )
        )
        return Resource.Success(data = null)
    }

    override fun connect() {
        Database.connect(url = databaseInfo.url, driver = databaseInfo.driver)
        SchemaDefinition.createSchema()
    }

    override fun insertAccount(account: Account): Boolean {
        return transaction {
            Accounts.insertIgnore {
                it.insert(account)
            }.insertedCount == 1
        }
    }

    override suspend fun getAccount(username: String, password: String): Account? {
        return transaction {
            Accounts.select {
                (Accounts.username eq username) and (Accounts.password eq password)
            }.map {
                it.toAccount()
            }.firstOrNull()
        }
    }

    fun getClientId(client: Client): Int {
        return transaction {
            Clients.select {
                Clients.medicareNumber eq client.medicareNumber.number
            }.map {
                it[Clients.id]
            }.firstOrNull() ?: -1
        }
    }

    override fun getClient(clientId: Int): Client? {
        return transaction {
            Clients.select {
                Clients.id eq clientId
            }.map {
                it.toClient(getRequisitions(clientId))
            }.firstOrNull()
        }
    }

    override fun insertClient(client: Client): Client {
        val insertedPatientId = transaction {
            Clients.insert {
                it.insert(client)
            } get Clients.id
        }

        client.requisitions.forEach { requisition ->
            insertRequisition(requisition, insertedPatientId, client.medicareNumber)
        }

        return client.copy(id = insertedPatientId)
    }

    override fun getClients(): List<Client> {
        return transaction {
            Clients
                .selectAll()
                .orderBy(Clients.createdOn)
                .map { it.toClient(getRequisitions(it[Clients.id])) }
        }
    }

    override fun insertRequisition(requisition: Requisition, clientId: Int, medicareNumber: MedicareNumber) {
        transaction {
            Requisitions.insert {
                it.insert(requisition, clientId)
            }
        }

        val filePath = "${databaseInfo.directory}/documents/${medicareNumber.number}/requisitions/"
        val directory = File(filePath)
        directory.mkdirs()

        val file = File(filePath, requisition.path.substringAfterLast('/'))
        file.createNewFile()

        File(requisition.path).inputStream().use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }

    private fun getRequisitions(patientId: Int): List<Requisition> =
        Requisitions
            .select { Requisitions.patientId eq patientId }
            .map { it.toRequisition() }

    override suspend fun updateClientQuickbooksId(clientId: Int, quickbooksId: Int) {
        transaction {
            Clients.update({ Clients.id eq clientId }) {
                it[this.quickbooksId] = quickbooksId
            }
        }
    }

    override fun updateClientRequisitions(clientId: Int, requisition: Requisition) {
        transaction {
            Requisitions.update({ Requisitions.id eq requisition.id }) {
                it[path] = requisition.path
                it[typeId] = requisition.typeId
                it[typeName] = requisition.typeName
                it[testedOn] = requisition.testedOn
            }
        }
    }

    override fun deleteRequisition(requisition: Requisition) {
        transaction {
            Requisitions.deleteWhere { Requisitions.id eq requisition.id }
        }
    }

    override fun addRequisition(clientId: Int, requisition: Requisition, medicareNumber: MedicareNumber) {
        transaction {
            insertRequisition(requisition, clientId, medicareNumber)
        }
    }
}