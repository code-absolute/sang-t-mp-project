package tech.codeabsolute.presentation.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.TableCellText(
    text: String,
    weight: Float,
    color: Color = Color.Black
) {
    Text(
        text = text,
        color = color,
        modifier = Modifier
            .fillMaxHeight()
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}