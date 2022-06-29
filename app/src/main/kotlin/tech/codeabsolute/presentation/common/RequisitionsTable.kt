package tech.codeabsolute.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.joda.time.format.DateTimeFormat
import org.koin.java.KoinJavaComponent
import tech.codeabsolute.model.Client
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.presentation.requisitions_table.RequisitionsTableEvent
import tech.codeabsolute.presentation.requisitions_table.RequisitionsTableViewModel
import tech.codeabsolute.presentation.requisitions_table.add_requisition_dialog.AddRequisitionDialog
import tech.codeabsolute.ui.theme.LightThemePrimary
import java.awt.Desktop
import java.io.IOException

@Composable
fun RequisitionsTable(
    client: Client? = null,
    viewModel: RequisitionsTableViewModel = KoinJavaComponent.get(RequisitionsTableViewModel::class.java),
    onRequisitionsListChanged: (Requisition, List<Requisition>) -> Unit,
    onRequisitionDeleted: (Requisition) -> Unit,
    onAddRequisition: (Requisition) -> Unit
) {

    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.onEvent(RequisitionsTableEvent.LoadExistingRequisitions(client?.requisitions))
    }

    Column {
        Row(Modifier.background(LightThemePrimary)) {
            TableCellText(text = "#", weight = 0.3f, color = Color.White)
            TableCellText(text = "Type", weight = 1f, color = Color.White)
            TableCellText(text = "Upload Date", weight = 2f, color = Color.White)
            TableCellText(text = "Test Date", weight = 2f, color = Color.White)
            TableCellText(text = "Actions", weight = 2f, color = Color.White)
        }

        if (uiState.isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalArrangement = Arrangement.End
            ) {
                CircularProgressIndicator()
            }
        } else {
            val dateFormatMedium = DateTimeFormat.mediumDateTime()

            uiState.requisitions.forEachIndexed { index, requisition ->
                val backgroundColor = if (index % 2 == 0) Color.White else Color.LightGray
                Row(Modifier.height(IntrinsicSize.Min).background(backgroundColor)) {
                    TableCellText(text = "${index + 1}", weight = 0.3f)
                    TableCellText(text = requisition.typeName, weight = 1f)
                    TableCellText(text = dateFormatMedium.print(requisition.createdOn), weight = 2f)
                    if (requisition.testedOn == null) {
                        TableCellText(text = "Not tested", weight = 2f)
                    } else {
                        TableCellText(text = dateFormatMedium.print(requisition.testedOn), weight = 2f)
                    }
                    TableCellActions(
                        quickbooksClientId = client?.quickbooksId, requisition = requisition,
                        { viewModel.onEvent(RequisitionsTableEvent.OpenEditRequisitionDialog(index)) },
                        { requisition ->
                            viewModel.onEvent(
                                RequisitionsTableEvent.OnDeleteRequisition(
                                    requisition,
                                    onRequisitionsListChanged
                                )
                            )
                            onRequisitionDeleted(requisition)
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalArrangement = Arrangement.End
            ) {
                if (uiState.isAddRequisitionDialogOpen) {
                    AddRequisitionDialog(
                        onAdd = {
                            if (uiState.requisitionToEditIndex == null) {
                                viewModel.onEvent(RequisitionsTableEvent.AddRequisition(it, onRequisitionsListChanged, onAddRequisition))
                            } else {
                                viewModel.onEvent(RequisitionsTableEvent.EditRequisition(it, onRequisitionsListChanged))
                            }
                        },
                        onClose = { viewModel.onEvent(RequisitionsTableEvent.CloseAddRequisitionDialog) },
                        uiState.requisitionToEditIndex?.let { uiState.requisitions[it] }
                    )
                }

                Button(onClick = {
                    viewModel.onEvent(RequisitionsTableEvent.OpenAddRequisitionDialog)
                }) {
                    Text("Add")
                }
            }
        }
    }
}

@Composable
fun RowScope.TableCellActions(
    quickbooksClientId: Int? = null,
    requisition: Requisition,
    onEditClicked: () -> Unit,
    onDeleteClicked: (Requisition) -> Unit
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
                onEditClicked()
            },
            Modifier.weight(1f)
        ) {
            Text("Edit")
        }

        Button(
            onClick = {
                onDeleteClicked(requisition)
            },
            Modifier.weight(1f)
        ) {
            Text("Delete")
        }
    }
}
