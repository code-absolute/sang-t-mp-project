package tech.codeabsolute.use_cases.get_authentication_url

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.codeabsolute.client.OAuth2Client
import tech.codeabsolute.model.Url
import tech.codeabsolute.util.random

@ExtendWith(MockKExtension::class)
class GetQuickbooksAuthenticationRequestUrlTest {

    @MockK
    private lateinit var oAuth2Client: OAuth2Client

    private lateinit var useCase: GetQuickbooksAuthenticationRequestUrlUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetQuickbooksAuthenticationRequestUrl(oAuth2Client)
    }

    @Test
    fun `Get authorization request url`() = runBlocking {

        val expectedOutput = Url(String.random())
        every { oAuth2Client.getAuthorizationRequestUrl() } returns expectedOutput

        val output = useCase()

        assertEquals(expectedOutput, output)
    }
}