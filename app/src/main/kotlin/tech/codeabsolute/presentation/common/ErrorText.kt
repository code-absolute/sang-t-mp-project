package tech.codeabsolute.presentation.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ColumnScope.ErrorText(error: String?) {
    error?.let {
        Text(
            text = it,
            color = MaterialTheme.colors.error,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun RowScope.ErrorText(error: String?) {
    error?.let {
        Text(
            text = it,
            color = MaterialTheme.colors.error
        )
    }
}