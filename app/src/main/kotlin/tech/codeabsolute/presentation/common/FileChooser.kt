package tech.codeabsolute.presentation.common

import androidx.compose.runtime.Composable
import tech.codeabsolute.presentation.new_client.FileDialog

@Composable
fun FileChooser(
    isOpen: Boolean,
    onFileSelected: (String?) -> Unit
) {
    if (isOpen) {
        FileDialog(
            onCloseRequest = {
                onFileSelected(it)
            }
        )
    }
}