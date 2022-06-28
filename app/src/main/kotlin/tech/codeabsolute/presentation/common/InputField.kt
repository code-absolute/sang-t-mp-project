package tech.codeabsolute.presentation.common

import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    isPassword: Boolean = false,
    isError: Boolean = false,
    leadingIcon: ImageVector,
    onTextChanged: (String) -> Unit
) {
    var textFieldState by remember { mutableStateOf(TextFieldValue(text)) }

    OutlinedTextField(
        value = textFieldState,
        onValueChange = {
            textFieldState = it
            onTextChanged(textFieldState.text)
        },
        modifier = modifier,
        placeholder = { Text(text = placeholder) },
        isError = isError,
        singleLine = true,
        visualTransformation = if (isPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        ),
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = leadingIcon.name)
        }
    )
}
