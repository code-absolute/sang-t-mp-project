package tech.codeabsolute.presentation.requisitions_table.add_requisition_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import org.koin.java.KoinJavaComponent
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.model.invoice_item.Item
import tech.codeabsolute.presentation.common.FileChooser
import tech.codeabsolute.presentation.new_client.NewClientFormField
import tech.codeabsolute.ui.theme.LightThemePrimary
import tech.codeabsolute.util.empty

@Composable
fun AddRequisitionDialog(
    onAdd: (Requisition) -> Unit,
    onClose: () -> Unit,
    requisition: Requisition? = null,
    viewModel: AddRequisitionDialogViewModel = KoinJavaComponent.get(AddRequisitionDialogViewModel::class.java),
) {

    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.onEvent(AddRequisitionDialogEvent.InitDialog(requisition))
    }

    Dialog(
        title = "New requisition",
        state = DialogState(size = DpSize(512.dp, 512.dp)),
        onCloseRequest = { onClose() },
    ) {
        Column(
            Modifier.fillMaxSize().padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                RequisitionFileRow(
                    uiState
                ) { viewModel.onEvent(AddRequisitionDialogEvent.OnFileSelected(it)) }
                RequisitionTypesRow(uiState.requisitionTypes, uiState.requisitionType) {
                    viewModel.onEvent(AddRequisitionDialogEvent.OnTypeChanged(it))
                }
                RequisitionTestDateRow(
                    uiState,
                    { viewModel.onEvent(AddRequisitionDialogEvent.OnIsTestedCheckChanged(it)) },
                    { viewModel.onEvent(AddRequisitionDialogEvent.OnTestDateChanged(it)) },
                    { viewModel.onEvent(AddRequisitionDialogEvent.OnTestTimeChanged(it)) },
                    { viewModel.onEvent(AddRequisitionDialogEvent.OnTestAMPMChanged(it)) }
                )
                ButtonsRow({
                    viewModel.onEvent(AddRequisitionDialogEvent.OnAddClicked(onAdd))
                    onClose()
                }, {
                    onClose()
                })
            }
        }
    }
}

@Composable
fun RequisitionFileRow(
    uiState: AddRequisitionDialogState,
    onFileSelected: (String) -> Unit,
) {

    var isFileChooserOpen: Boolean by remember { mutableStateOf(false) }

    Row {
        Text("File", fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = uiState.displayedRequisitionPath,
            onValueChange = {},
            modifier = Modifier.background(color = Color.White).weight(1f),
            enabled = false,
        )
        Button(onClick = {
            isFileChooserOpen = true
        }) {
            Text("Choose PDF")
        }
        FileChooser(
            isOpen = isFileChooserOpen,
            onFileSelected = {
                onFileSelected(it ?: String.empty())
                isFileChooserOpen = false
            }
        )
    }
}

@Composable
fun RequisitionTypesRow(
    requisitionTypes: List<Item>,
    selectedRequisitionType: Item,
    onTypeChanged: (Item) -> Unit
) {
    Row {
        Text("Type", fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
    Row(Modifier.fillMaxWidth()) {
        RequisitionTypesDropDown(requisitionTypes, selectedRequisitionType) {
            onTypeChanged(it)
        }
    }
}

@Composable
fun RequisitionTypesDropDown(
    items: List<Item>,
    selectedRequisitionType: Item,
    selectedType: (Item) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(items.indexOf(selectedRequisitionType)) }

    Row(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .clickable(onClick = { expanded = true })
    ) {
        if (items.isNotEmpty()) {
            OutlinedTextField(
                value = items[selectedIndex].name,
                onValueChange = {},
                modifier = Modifier.background(color = Color.White).fillMaxWidth(),
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Drop down",
                        modifier = Modifier.size(32.dp)
                    )
                }
            )
            DropdownMenu(
                modifier = Modifier.align(Alignment.CenterVertically).fillMaxWidth().padding(8.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selectedType(item)
                            selectedIndex = index
                            expanded = false
                        }) {
                        Text(text = item.name, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Composable
fun RequisitionTestDateRow(
    uiState: AddRequisitionDialogState,
    onIsTestedCheckChanged: (Boolean) -> Unit,
    onTestDateChanged: (String) -> Unit,
    onTestTimeChanged: (String) -> Unit,
    onTestAMPMChanged: (String) -> Unit
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            "Test Date (optional)",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Checkbox(
            uiState.isTested,
            colors = CheckboxDefaults.colors(checkedColor = LightThemePrimary),
            onCheckedChange = {
                onIsTestedCheckChanged(it)
            })
    }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DateFormField(uiState, onTestDateChanged)
        TimeFormField(uiState, onTestTimeChanged)
//        AMPMDropDown(uiState.isTested) {
//            onTestAMPMChanged(it)
//        }
    }
}

@Composable
fun RowScope.DateFormField(
    uiState: AddRequisitionDialogState,
    onTestDateChanged: (String) -> Unit
) {
    NewClientFormField(
        textFieldValue = uiState.testDateTextFieldValue,
        error = null,
        modifier = Modifier.weight(1f),
        enabled = uiState.isTested,
        labelText = "Date (YYYY/MM/DD)"
    ) {
        onTestDateChanged(it.text)
    }
}

@Composable
fun RowScope.TimeFormField(
    uiState: AddRequisitionDialogState,
    onTestTimeChanged: (String) -> Unit
) {
    NewClientFormField(
        textFieldValue = uiState.testTimeTextFieldValue,
        error = null,
        modifier = Modifier.weight(1f),
        enabled = uiState.isTested,
        labelText = "Time (24:00)"
    ) {
        onTestTimeChanged(it.text)
    }
}

@Composable
fun ButtonsRow(
    onAdd: () -> Unit,
    onClose: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            modifier = Modifier.padding(end = 8.dp),
            onClick = { onClose() }) {
            Text("Cancel")
        }
        Button(onClick = { onAdd() }) {
            Text("Save")
        }
    }
}

@Composable
fun RowScope.AMPMDropDown(isTested: Boolean, selected: (String) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    val items = listOf("AM", "PM")

    Column(
        modifier = Modifier
            .weight(0.5f)
            .padding(top = 8.dp)
            .background(color = Color.White)
            .clickable(enabled = isTested, onClick = { expanded = true })
    ) {
        OutlinedTextField(
            value = items[selectedIndex],
            onValueChange = {},
            modifier = Modifier.background(color = Color.White),
            enabled = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Drop down",
                    modifier = Modifier.size(32.dp)
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    modifier = Modifier,
                    onClick = {
                        selected(item)
                        selectedIndex = index
                        expanded = false
                    }) {
                    Text(text = item, modifier = Modifier)
                }
            }
        }
    }
}