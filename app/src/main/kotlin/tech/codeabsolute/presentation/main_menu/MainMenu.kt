package tech.codeabsolute.presentation.main_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.LogOut
import org.koin.java.KoinJavaComponent
import tech.codeabsolute.ui.theme.LightThemePrimaryVariant

@Composable
fun MainMenu(
    viewModel: MainMenuViewModel = KoinJavaComponent.get(MainMenuViewModel::class.java),
    onSectionChange: (Section) -> Unit,
    onLogout: () -> Unit
) {

    val uiState = viewModel.uiState

    Column(
        modifier = Modifier.shadow(elevation = 4.dp).width(256.dp).fillMaxHeight()
            .background(MaterialTheme.colors.primary)
    ) {
        Title()
        Welcome(uiState)
        Menu(
            uiState,
            { viewModel.onEvent(it, onSectionChange) },
            { viewModel.onEvent(MainMenuEvent.Logout(onLogout)) }
        )
    }
}

@Composable
fun ColumnScope.Title() {
    Text(
        text = "Sang-T MP",
        color = Color.White,
        fontSize = 32.sp,
        modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 16.dp)
    )
}

@Composable
fun Welcome(uiState: MainMenuViewState) {
    Column(Modifier.fillMaxWidth().background(MaterialTheme.colors.primaryVariant)) {
        Text(
            text = uiState.formattedDate,
            color = Color.Black,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp, bottom = 4.dp)
        )
        Text(
            text = uiState.formattedTime,
            color = Color.Black,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
        )
    }
}

@Composable
fun Menu(
    uiState: MainMenuViewState,
    onMenuItemClicked: (MainMenuEvent) -> Unit,
    onLogout: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        uiState.menuItems.forEach { menuItem ->
            MenuItemButton(text = menuItem.text, active = menuItem.active, icon = menuItem.icon, onClick = {
                onMenuItemClicked(menuItem.event)
            })
        }
        LogoutButton { onLogout() }
    }
}

@Composable
fun MenuItemButton(
    modifier: Modifier = Modifier, text: String, active: Boolean, icon: ImageVector, onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 4.dp),
        colors = if (active) {
            ButtonDefaults.buttonColors(backgroundColor = LightThemePrimaryVariant)
        } else {
            ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
        }
    ) {
        val color = if (active) {
            Color.White
        } else {
            Color.LightGray
        }

        Icon(imageVector = icon, contentDescription = icon.name, tint = color)
        Text(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
            textAlign = TextAlign.Start,
            text = text,
            color = color,
            fontSize = 16.sp
        )
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 4.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
    ) {
        Icon(imageVector = FeatherIcons.LogOut, contentDescription = "Logout", tint = Color.LightGray)
        Text(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
            textAlign = TextAlign.Start,
            text = "Log Out",
            color = Color.LightGray,
            fontSize = 16.sp
        )
    }
}