package tech.codeabsolute.repository

import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.codeabsolute.data.local.dao.ClientsDao
import tech.codeabsolute.model.Client
import tech.codeabsolute.model.SearchAttributes
import tech.codeabsolute.util.Resource
import tech.codeabsolute.util.empty
import tech.codeabsolute.util.random

@ExtendWith(MockKExtension::class)
class ClientsRepositoryImplTest {

    @MockK
    private lateinit var clientsDao: ClientsDao

    private lateinit var repository: ClientsRepository

    @BeforeEach
    fun setUp() {
        repository = ClientsRepositoryImpl(clientsDao)
    }

    @Test
    fun `Get client returns Loading`() = runBlocking {

        val resource = repository.getClient(Int.random()).first()

        assertTrue(resource is Resource.Loading)
        assertNull(resource.data)
    }

    @Test
    fun `Get client returns Success`() = runBlocking {

        val clientId = Int.random()
        val expectedClient = mockk<Client>()
        coEvery { clientsDao.getClient(clientId) } returns expectedClient

        val resource = repository.getClient(clientId).last()

        assertTrue(resource is Resource.Success)
        assertEquals(expectedClient, resource.data)
    }

    @Test
    fun `Get client returns Error when exception is thrown`() = runBlocking {

        val clientId = Int.random()
        val expectedMessage = String.random()
        coEvery { clientsDao.getClient(clientId) } throws Exception(expectedMessage)

        val resource = repository.getClient(clientId).last()

        assertTrue(resource is Resource.Error)
        assertNull(resource.data)
        assertEquals(expectedMessage, resource.message)
    }

    @Test
    fun `Get clients returns Loading`() = runBlocking {

        val searchAttributes = mockk<SearchAttributes>()

        val resource = repository.getClients(searchAttributes).first()

        assertTrue(resource is Resource.Loading)
        assertNull(resource.data)
    }

    @Test
    fun `Get clients returns Success`() = runBlocking {

        val expectedClients = List(10) { mockk<Client>() }
        val searchAttributes = mockk<SearchAttributes> {
            every { searchTerm } returns String.random()
        }
        coEvery { clientsDao.getClients(searchAttributes) } returns expectedClients

        val resource = repository.getClients(searchAttributes).last()

        assertTrue(resource is Resource.Success)
        assertEquals(expectedClients, resource.data)
    }

    @Test
    fun `Get clients returns Success when search term is an empty string`() = runBlocking {

        val expectedClients = emptyList<Client>()
        val searchAttributes = mockk<SearchAttributes> {
            every { searchTerm } returns String.empty()
        }
        coEvery { clientsDao.getClients(searchAttributes) } returns expectedClients

        val resource = repository.getClients(searchAttributes).last()

        assertTrue(resource is Resource.Success)
        assertEquals(expectedClients, resource.data)
    }

    @Test
    fun `Get clients returns Error when exception is thrown`() = runBlocking {

        val searchAttributes = mockk<SearchAttributes> {
            every { searchTerm } returns String.random()
        }
        val expectedMessage = String.random()
        coEvery { clientsDao.getClients(searchAttributes) } throws Exception(expectedMessage)

        val resource = repository.getClients(searchAttributes).last()

        assertTrue(resource is Resource.Error)
        assertNull(resource.data)
        assertEquals(expectedMessage, resource.message)
    }

    @Test
    fun `Insert client returns Loading`() = runBlocking {

        val client = mockk<Client>()

        val resource = repository.createClient(client).first()

        assertTrue(resource is Resource.Loading)
        assertNull(resource.data)
    }

    @Test
    fun `Insert client returns Success`() = runBlocking {

        val client = mockk<Client>()
        val expectedClient = mockk<Client>()
        coEvery { clientsDao.insertClient(client) } returns expectedClient

        val resource = repository.createClient(client).last()

        assertTrue(resource is Resource.Success)
        assertEquals(expectedClient, resource.data)
    }

    @Test
    fun `Insert client returns Error when exception is thrown`() = runBlocking {

        val client = mockk<Client>()
        val expectedMessage = String.random()
        coEvery { clientsDao.insertClient(client) } throws Exception(expectedMessage)

        val resource = repository.createClient(client).last()

        assertTrue(resource is Resource.Error)
        assertNull(resource.data)
        assertEquals(expectedMessage, resource.message)
    }

}