package tech.codeabsolute.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.format.DateTimeFormat
import org.koin.java.KoinJavaComponent
import tech.codeabsolute.data.QuickbooksDataSource
import tech.codeabsolute.model.*
import tech.codeabsolute.ui.theme.LightThemePrimary
import java.awt.Desktop
import java.io.IOException

@Composable
fun RequisitionsTable(client: Client) {
    Column {
        Row(Modifier.background(LightThemePrimary)) {
            TableCellText(text = "#", weight = 0.5f, color = Color.White)
            TableCellText(text = "Upload Date", weight = 2f, color = Color.White)
            TableCellText(text = "Test Date", weight = 2f, color = Color.White)
            TableCellText(text = "Actions", weight = 2f, color = Color.White)
        }

        val dateFormatMedium = DateTimeFormat.mediumDateTime()

        client.requisitions.forEachIndexed { index, requisition ->
            val backgroundColor = if (index % 2 == 0) Color.White else Color.LightGray
            Row(Modifier.height(IntrinsicSize.Min).background(backgroundColor)) {
                TableCellText(text = "${index + 1}", weight = 0.5f)
                TableCellText(text = dateFormatMedium.print(requisition.createdOn), weight = 2f)
                TableCellText(text = dateFormatMedium.print(requisition.testedOn), weight = 2f)
                TableCellActions(client.id, requisition) {}
            }
        }
    }
}

@Composable
fun RowScope.TableCellActions(
    clientId: Int? = null,
    requisition: Requisition,
    onEditClicked: (Requisition) -> Unit
) {
    Row(
        Modifier.weight(2f)
            .border(1.dp, Color.Black)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().open(requisition.toFile())
                    } catch (ex: IOException) {
                        // no application registered for PDFs
                    }
                }
            },
            Modifier.weight(1f)
        ) {
            Text("View")
        }

        Button(
            onClick = {
                onEditClicked(requisition)
            }
        ) {
            Text("Edit")
        }

        if (clientId != null) {
            Button(
                onClick = {
                    createInvoiceForClient(clientId)
                },
                Modifier.weight(1f)
            ) {
                Text("Invoice")
            }
        }
    }
}

fun createInvoiceForClient(clientId: Int) {
    val quickbooksDataSource: QuickbooksDataSource = KoinJavaComponent.get(QuickbooksDataSource::class.java)

    CoroutineScope(Dispatchers.IO).launch {
        quickbooksDataSource.createInvoice(
            InvoiceRequest(
                CustomerRef(clientId.toString()),
                listOf(
                    Line(
                        40.0,
                        "SalesItemLineDetail",
                        SalesItemLineDetail(
                            ItemRef(
                                "Services",
                                "1"
                            )
                        )
                    )
                )
            )
        )
    }
}