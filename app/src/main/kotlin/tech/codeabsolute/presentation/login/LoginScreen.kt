package tech.codeabsolute.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Lock
import compose.icons.feathericons.User
import org.koin.java.KoinJavaComponent
import tech.codeabsolute.presentation.common.ErrorText
import tech.codeabsolute.presentation.common.InputField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = KoinJavaComponent.get(LoginViewModel::class.java),
    onSuccessfulLogin: () -> Unit
) {

    val uiState = viewModel.uiState
    val (focusUsernameField, focusPasswordField) = FocusRequester.createRefs()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    viewModel.onEvent(LoginFormEvent.Login(onSuccessfulLogin))
                }
                false
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(4), clip = true)
                .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4))
                .clip(RoundedCornerShape(4))
                .width(320.dp)
                .background(Color.White)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Logo()
            UsernameField(
                uiState,
                focusUsernameField,
                focusPasswordField
            ) { viewModel.onEvent(LoginFormEvent.UsernameChanged(it)) }
            PasswordField(
                uiState,
                focusPasswordField,
                focusUsernameField
            ) { viewModel.onEvent(LoginFormEvent.PasswordChanged(it)) }
            LoginButton { viewModel.onEvent(LoginFormEvent.Login(onSuccessfulLogin)) }

            uiState.loginError?.let {
                ErrorText(it)
            }
        }
    }
}

@Composable
fun Logo() {
    Text(
        text = "Sang-T MP", fontWeight = FontWeight.Bold, fontSize = 32.sp
    )
}

@Composable
fun UsernameField(
    uiState: LoginViewState,
    focusUsernameField: FocusRequester,
    focusPasswordField: FocusRequester,
    onUserNameChanged: (String) -> Unit
) {
    val focusRequester = FocusRequester()
    InputField(
        text = uiState.username,
        modifier = Modifier
            .padding(top = 16.dp)
            .focusRequester(focusRequester)
            .focusOrder(focusUsernameField) { next = focusPasswordField },
        placeholder = "Username",
        leadingIcon = FeatherIcons.User
    ) { onUserNameChanged(it) }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun PasswordField(
    uiState: LoginViewState,
    focusPasswordField: FocusRequester,
    focusUsernameField: FocusRequester,
    onPasswordChanged: (String) -> Unit
) {
    InputField(
        text = uiState.password,
        modifier = Modifier
            .padding(top = 8.dp)
            .focusOrder(focusPasswordField) { next = focusUsernameField },
        placeholder = "Password",
        isPassword = true,
        leadingIcon = FeatherIcons.Lock
    ) { onPasswordChanged(it) }
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        content = { Text("Login") }
    )
}
