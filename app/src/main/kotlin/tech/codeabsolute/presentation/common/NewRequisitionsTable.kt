package tech.codeabsolute.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.joda.time.format.DateTimeFormat
import org.koin.java.KoinJavaComponent
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.model.invoice_item.Item
import tech.codeabsolute.presentation.requisitions_table.RequisitionsTableEvent
import tech.codeabsolute.presentation.requisitions_table.RequisitionsTableViewModel
import tech.codeabsolute.presentation.requisitions_table.add_requisition_dialog.AddRequisitionDialog
import tech.codeabsolute.ui.theme.LightThemePrimary

@Composable
fun NewRequisitionsTable(
    requisitions: List<Requisition>? = null,
    viewModel: RequisitionsTableViewModel = KoinJavaComponent.get(RequisitionsTableViewModel::class.java),
    onRequisitionsListChanged: (List<Requisition>) -> Unit
) {

    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.onEvent(RequisitionsTableEvent.LoadExistingRequisitions(requisitions))
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
                    TableCellActions(requisition = requisition) {
                        viewModel.onEvent(RequisitionsTableEvent.OpenEditRequisitionDialog(it))
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalArrangement = Arrangement.End
            ) {
                if (uiState.isAddRequisitionDialogOpen) {
                    AddRequisitionDialog(
                        onAdd = {
                            if (uiState.requisitionToEdit == null) {
                                viewModel.onEvent(RequisitionsTableEvent.AddRequisition(it, onRequisitionsListChanged))
                            } else {
                                viewModel.onEvent(RequisitionsTableEvent.EditRequisition(it, onRequisitionsListChanged))
                            }
                        },
                        onClose = { viewModel.onEvent(RequisitionsTableEvent.CloseAddRequisitionDialog) },
                        uiState.requisitionToEdit
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
fun RowScope.TableCellDropDown(
    items: List<Item>,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .weight(1f)
            .border(1.dp, Color.Black)
            .padding(8.dp)
            .fillMaxSize()
            .clickable(onClick = { expanded = true })
    ) {
        Text(items[selectedIndex].name, modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = "Drop down",
            modifier = Modifier.size(32.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }) {
                    Text(text = item.name)
                }
            }
        }
    }
}

