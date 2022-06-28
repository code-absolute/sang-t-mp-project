package tech.codeabsolute.presentation.new_client

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import tech.codeabsolute.presentation.common.ErrorText

@Composable
fun NewClientFormField(
    value: String,
    error: String?,
    modifier: Modifier,
    labelText: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    Column(modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            enabled = enabled,
            isError = error != null,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = labelText) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            )
        )
        ErrorText(error)
    }
}

@Composable
fun NewClientFormField(
    textFieldValue: MutableState<TextFieldValue>,
    error: String?,
    modifier: Modifier,
    labelText: String,
    enabled: Boolean = true,
    onValueChange: (TextFieldValue) -> Unit
) {
    Column(modifier) {
        OutlinedTextField(
            value = textFieldValue.value,
            onValueChange = onValueChange,
            singleLine = true,
            enabled = enabled,
            isError = error != null,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = labelText) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            )
        )
        ErrorText(error)
    }
}

@Composable
fun NewClientFormField(
    textFieldValue: TextFieldValue,
    error: String?,
    modifier: Modifier,
    labelText: String,
    enabled: Boolean = true,
    onValueChange: (TextFieldValue) -> Unit
) {
    Column(modifier) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = onValueChange,
            singleLine = true,
            enabled = enabled,
            isError = error != null,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = labelText) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            )
        )
        ErrorText(error)
    }
}