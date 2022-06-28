package tech.codeabsolute.repository

import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tech.codeabsolute.util.Resource
import tech.codeabsolute.util.random

class RequisitionsRepositoryImplTest {

    private lateinit var repository: RequisitionsRepository

    @BeforeEach
    fun setUp() {
        repository = RequisitionsRepositoryImpl()
    }

    @Test
    fun `Add requisition returns Loading`() = runBlocking {

        val resource = repository.addRequisition(mockk(), Int.random()).first()

        assertTrue(resource is Resource.Loading)
        assertNull(resource.data)
    }

    @Test
    fun `Get requisitions returns Loading`() = runBlocking {

        val resource = repository.getRequisitions(Int.random()).first()

        assertTrue(resource is Resource.Loading)
        assertNull(resource.data)
    }
}