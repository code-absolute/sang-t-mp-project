package tech.codeabsolute.use_cases.set_access_tokens

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.codeabsolute.client.OAuth2Client
import tech.codeabsolute.model.AuthResult
import java.net.URI

@ExtendWith(MockKExtension::class)
internal class SetAccessTokensTest {

    @MockK
    private lateinit var oAuth2Client: OAuth2Client

    private lateinit var useCase: SetQuickbooksAccessTokensUseCase

    @BeforeEach
    fun setUp() {
        useCase = SetQuickbooksAccessTokens(oAuth2Client)
    }

    @Test
    fun `Set access tokens`() = runBlocking {

        val expectedOutput = mockk<AuthResult<Unit>>()
        val input = mockk<URI>()
        coEvery { oAuth2Client.setAccessTokens(input) } returns expectedOutput

        val output = useCase(input)

        assertEquals(expectedOutput, output)
    }
}