package tech.codeabsolute.presentation.clients

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import compose.icons.FeatherIcons
import compose.icons.feathericons.Search
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent
import tech.codeabsolute.model.Client
import tech.codeabsolute.model.SearchAttributes
import tech.codeabsolute.presentation.common.InputField
import tech.codeabsolute.presentation.common.RequisitionsTable
import tech.codeabsolute.presentation.common.TableCellText
import tech.codeabsolute.ui.theme.LightThemePrimary

@Composable
fun ClientsSection(
    viewModel: ClientsSectionViewModel = KoinJavaComponent.get(ClientsSectionViewModel::class.java),
    onClientDetailsClick: (Int) -> Unit
) {

    val uiState = viewModel.uiState

    SearchOptions(
        uiState,
        { viewModel.onEvent(ClientsSectionEvent.OnSearchFilterChanged(it)) },
        { viewModel.onEvent(ClientsSectionEvent.OnSearchOrderChanged(it)) },
        { viewModel.onEvent(ClientsSectionEvent.OnSearchQueryChanged(it)) }
    )
    ClientsTable(uiState, onClientDetailsClick)
}

@Composable
fun SearchOptions(
    uiState: ClientsSectionState,
    onSearchFilterChanged: (SearchAttributes.Filter) -> Unit,
    onSearchOrderChanged: (SearchAttributes.Order) -> Unit,
    onSearchQueryChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(4), clip = true)
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4))
            .clip(RoundedCornerShape(4))
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        SearchBar(uiState.searchQuery) { onSearchQueryChanged(it) }
        FiltersDropDown(
            listOf(
                SearchAttributes.Filter.MEDICARE_NUMBER,
                SearchAttributes.Filter.NAME
            )
        ) { onSearchFilterChanged(it) }
        OrderDropDown(
            listOf(
                SearchAttributes.Order.ASCENDING,
                SearchAttributes.Order.DESCENDING
            )
        ) { onSearchOrderChanged(it) }
    }
}

@Composable
fun RowScope.SearchBar(
    searchText: String,
    onSearch: (String) -> Unit
) {
    InputField(
        text = searchText,
        modifier = Modifier.weight(1f).padding(end = 8.dp),
        placeholder = "Search...",
        leadingIcon = FeatherIcons.Search,
    ) { onSearch(it) }
}

@Composable
fun FiltersDropDown(
    items: List<SearchAttributes.Filter>,
    changeSearchFilter: (SearchAttributes.Filter) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier.padding(8.dp)
            .width(200.dp)
            .clickable {
                expanded = !expanded
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(15))
                .clip(RoundedCornerShape(15))
                .background(Color.White)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = items[selectedIndex].text, modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    changeSearchFilter(items[selectedIndex])
                }) {
                    Text(text = s.text)
                }
            }
        }
    }
}

@Composable
fun RowScope.OrderDropDown(
    items: List<SearchAttributes.Order>,
    changeSearchFilter: (SearchAttributes.Order) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier.padding(8.dp)
            .width(200.dp)
            .clickable {
                expanded = !expanded
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(15))
                .clip(RoundedCornerShape(15))
                .background(Color.White)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = items[selectedIndex].text, modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    changeSearchFilter(items[selectedIndex])
                }) {
                    Text(text = s.text)
                }
            }
        }
    }
}

@Composable
fun ClientsTable(uiState: ClientsSectionState, onClientDetailsClick: (Int) -> Unit) {
    Column(modifier = Modifier.padding(top = 8.dp)) {
        ClientsTableHeaders()
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            if (uiState.clients.isEmpty()) {
                Text(
                    text = "No Results",
                    modifier = Modifier.padding(8.dp)
                )
            } else {
                ClientsTableResults(uiState, onClientDetailsClick)
            }
        }
    }
}

@Composable
fun ClientsTableHeaders() {
    Row(Modifier.background(LightThemePrimary).wrapContentHeight()) {
        Text(
            text = "Medicare Number",
            Modifier
                .border(1.dp, Color.Black)
                .weight(1f)
                .padding(8.dp),
            color = Color.White
        )
        Text(
            text = "Name",
            Modifier
                .border(1.dp, Color.Black)
                .weight(1f)
                .padding(8.dp),
            color = Color.White
        )
        Text(
            text = "Actions",
            Modifier
                .border(1.dp, Color.Black)
                .weight(1f)
                .padding(8.dp),
            color = Color.White
        )
    }
}

@Composable
fun ClientsTableResults(uiState: ClientsSectionState, onClientDetailsClick: (Int) -> Unit) {
    LazyColumn {
        itemsIndexed(uiState.clients) { index, client ->

            val openDialog = remember { mutableStateOf(false) }
            val openRequisitionsDialog = remember { mutableStateOf(false) }

//            if (openDialog.value) {
//                DetailsDialog(Modifier, openDialog, client)
//            }
            if (openRequisitionsDialog.value) {
                RequisitionsDialog().invoke(client) {
                    openRequisitionsDialog.value = false
                }
            }

            val backgroundColor = if (index % 2 == 0) Color.White else Color.LightGray

            Row(Modifier.height(IntrinsicSize.Min).background(backgroundColor)) {
                TableCellText(text = client.medicareNumber.toString(), weight = 1f)
                TableCellText(text = client.fullName.toString(), weight = 1f)
                Row(
                    Modifier.weight(1f)
                        .border(1.dp, Color.Black)
                        .padding(8.dp)
                ) {
                    Button(
                        onClick = { openRequisitionsDialog.value = true },
                        Modifier.weight(1f).padding(end = 8.dp)
                    ) {
                        Text("Requisitions")
                    }
                    Button(
                        onClick = { onClientDetailsClick(client.id) },
                        Modifier.weight(1f)
                    ) {
                        Text("Details")
                    }
                }
            }
        }
    }
}

@Single
class RequisitionsDialog {

    @Composable
    operator fun invoke(client: Client, onCloseRequest: () -> Unit = {}) {
        Dialog(
            onCloseRequest = onCloseRequest,
            title = "Requisitions",
            state = DialogState(width = 1000.dp, height = 600.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                RequisitionsTable(client)
            }
        }
    }
}