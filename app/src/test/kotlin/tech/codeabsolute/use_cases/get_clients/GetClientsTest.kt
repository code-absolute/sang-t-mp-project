package tech.codeabsolute.use_cases.get_clients

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.codeabsolute.model.Client
import tech.codeabsolute.model.SearchAttributes
import tech.codeabsolute.repository.ClientsRepository
import tech.codeabsolute.util.Resource

@ExtendWith(MockKExtension::class)
class GetClientsTest {

    @MockK
    private lateinit var clientsRepository: ClientsRepository

    private lateinit var useCase: GetClientsUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetClients(clientsRepository)
    }

    @Test
    fun `Get clients from clients repository with search attributes`() = runBlocking {

        val expectedOutput = mockk<Flow<Resource<List<Client>>>>()
        val searchAttributes = mockk<SearchAttributes>()
        coEvery { clientsRepository.getClients(searchAttributes) } returns expectedOutput

        val output = useCase(searchAttributes)

        assertEquals(expectedOutput, output)
    }
}