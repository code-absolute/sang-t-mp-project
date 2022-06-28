package tech.codeabsolute.use_cases

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
import tech.codeabsolute.use_cases.authenticate_user.AuthenticateQuickbooksUser
import tech.codeabsolute.use_cases.authenticate_user.AuthenticateQuickbooksUserUseCase

@ExtendWith(MockKExtension::class)
class AuthenticateQuickbooksUserTest {

    @MockK
    private lateinit var oAuth2Client: OAuth2Client

    private lateinit var useCase: AuthenticateQuickbooksUserUseCase

    @BeforeEach
    fun setUp() {
        useCase = AuthenticateQuickbooksUser(oAuth2Client)
    }

    @Test
    fun `Authenticate quickbooks user`() = runBlocking {

        val expectedOutput = mockk<AuthResult<Unit>>()
        coEvery { oAuth2Client.authenticate() } returns expectedOutput

        val output = useCase()

        assertEquals(expectedOutput, output)
    }
}