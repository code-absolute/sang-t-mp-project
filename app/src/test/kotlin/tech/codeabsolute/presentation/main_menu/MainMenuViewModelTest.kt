package tech.codeabsolute.presentation.main_menu

import compose.icons.FeatherIcons
import compose.icons.feathericons.FileText
import compose.icons.feathericons.Settings
import compose.icons.feathericons.UserPlus
import compose.icons.feathericons.Users
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


@ExtendWith(MockKExtension::class)
class MainMenuViewModelTest {

    private lateinit var viewModel: MainMenuViewModel

    @BeforeEach
    fun setUp() {
        viewModel = MainMenuViewModel()
    }

    @Test
    fun `Create menu item Clients`() {

        val menuItem = MenuItem(
            text = "Clients",
            active = true,
            icon = FeatherIcons.Users,
            section = Section.Clients,
            event = MainMenuEvent.OpenClientsSection
        )

        val menuItems = viewModel.uiState.menuItems

        assertTrue(menuItems.contains(menuItem))
    }

    @Test
    fun `Create menu item New Client`() {

        val menuItem = MenuItem(
            text = "New Client",
            icon = FeatherIcons.UserPlus,
            section = Section.NewClient,
            event = MainMenuEvent.OpenNewClientSection
        )

        val menuItems = viewModel.uiState.menuItems

        assertTrue(menuItems.contains(menuItem))
    }

    @Test
    fun `Create menu item Reports`() {

        val menuItem = MenuItem(
            text = "Reports",
            icon = FeatherIcons.FileText,
            section = Section.Reports,
            event = MainMenuEvent.OpenReportsSection
        )

        val menuItems = viewModel.uiState.menuItems

        assertTrue(menuItems.contains(menuItem))
    }

    @Test
    fun `Create menu item Settings`() {

        val menuItem = MenuItem(
            text = "Settings",
            icon = FeatherIcons.Settings,
            section = Section.Settings,
            event = MainMenuEvent.OpenSettingsSection
        )

        val menuItems = viewModel.uiState.menuItems

        assertTrue(menuItems.contains(menuItem))
    }

    @ParameterizedTest
    @MethodSource("provideMainMenuEvents")
    fun `Main menu events`(event: MainMenuEvent) {

        val menuItems = getMenuItems().map {
            it.copy(active = (it.section == event.section))
        }
        val expectedUIState = MainMenuViewState(menuItems = menuItems)
        val onSectionChange = mockk<(Section) -> Unit>()
        justRun { event.section?.let(onSectionChange) }

        viewModel.onEvent(event, onSectionChange)
        val uiState = viewModel.uiState

        assertEquals(expectedUIState, uiState)
        verify { event.section?.let(onSectionChange) }
    }

    private fun getMenuItems() = listOf(
        MenuItem(
            text = "Clients",
            active = true,
            icon = FeatherIcons.Users,
            section = Section.Clients,
            event = MainMenuEvent.OpenClientsSection
        ),
        MenuItem(
            text = "New Client",
            icon = FeatherIcons.UserPlus,
            section = Section.NewClient,
            event = MainMenuEvent.OpenNewClientSection
        ),
        MenuItem(
            text = "Reports",
            icon = FeatherIcons.FileText,
            section = Section.Reports,
            event = MainMenuEvent.OpenReportsSection
        ),
        MenuItem(
            text = "Settings",
            icon = FeatherIcons.Settings,
            section = Section.Settings,
            event = MainMenuEvent.OpenSettingsSection
        )
    )

    @Test
    fun `Logout event`() {

        val expectedUIState = MainMenuViewState(menuItems = getMenuItems())
        val onLogout = mockk<() -> Unit>()
        val event = MainMenuEvent.Logout(onLogout)
        justRun { onLogout() }

        viewModel.onEvent(event)
        val uiState = viewModel.uiState

        verify { onLogout() }
        assertEquals(expectedUIState, uiState)
    }

    companion object {

        @JvmStatic
        private fun provideMainMenuEvents(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(MainMenuEvent.OpenClientsSection),
                Arguments.of(MainMenuEvent.OpenNewClientSection),
                Arguments.of(MainMenuEvent.OpenReportsSection),
                Arguments.of(MainMenuEvent.OpenSettingsSection)
            )
        }
    }
}