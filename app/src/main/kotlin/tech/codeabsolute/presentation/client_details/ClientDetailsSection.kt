package tech.codeabsolute.presentation.client_details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.joda.time.format.DateTimeFormat
import org.koin.java.KoinJavaComponent
import tech.codeabsolute.model.Client
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.presentation.common.RequisitionsTable

@Composable
fun ClientDetailsSection(
    clientId: Int?,
    viewModel: ClientDetailsSectionViewModel = KoinJavaComponent.get(ClientDetailsSectionViewModel::class.java),
) {
    val uiState = viewModel.uiState

    LaunchedEffect(clientId) {
        viewModel.loadClient(clientId)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.errorMessage != null || uiState.client == null) {
            Text("Could not load client ${uiState.errorMessage ?: ""}")
        } else {
            val client = uiState.client
            Row(
                modifier = Modifier.wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                ClientDetailsInfo(client)
                ClientDetailsContactInfo(client)
            }
            if (client.address != null) {
                ClientDetailsAddress(client)
            }
            ClientDetailsRequisitions(client,
                { requisition ->
                    viewModel.updateRequisitions(client.id, requisition)
                }, {
                    viewModel.deleteRequisition(it)
                }, {
                    viewModel.addRequisition(client.id, it)
                })
        }
    }
}

@Composable
fun ClientDetailsInfo(client: Client) {
    Column(
        modifier = Modifier.shadow(elevation = 6.dp, shape = RoundedCornerShape(4), clip = true)
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4))
            .clip(RoundedCornerShape(4)).background(Color.White).padding(16.dp).fillMaxWidth(0.5f),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Client Info", fontWeight = FontWeight.Bold, fontSize = 28.sp)
        Text("Given name: ${client.fullName.firstName}")
        Text("Family name: ${client.fullName.lastName}")
        Text("Medicare number: ${client.medicareNumber}")
        val dateFormatShort = DateTimeFormat.shortDate()
        Text("Date of birth: ${dateFormatShort.print(client.dateOfBirth.toDateTime())}")
    }
}

@Composable
fun ClientDetailsContactInfo(client: Client) {
    Column(
        modifier = Modifier.shadow(elevation = 6.dp, shape = RoundedCornerShape(4), clip = true)
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4))
            .clip(RoundedCornerShape(4)).background(Color.White).padding(16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Contact Info", fontWeight = FontWeight.Bold, fontSize = 28.sp)
        Text("Phone Number: ${client.contactInfo.phoneNumber}")

        if (client.contactInfo.otherNumber.number.isEmpty()) {
            Text("Other Number: -")
        } else {
            Text("Other Number: ${client.contactInfo.otherNumber}")
        }

        if (client.contactInfo.emailAddress.email.isEmpty()) {
            Text("Email: -")
        } else {
            Text("Email: ${client.contactInfo.emailAddress}")
        }
    }
}

@Composable
fun ClientDetailsAddress(client: Client) {
    Column(
        modifier = Modifier.shadow(elevation = 6.dp, shape = RoundedCornerShape(4), clip = true)
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4))
            .clip(RoundedCornerShape(4)).background(Color.White).padding(16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Address", fontWeight = FontWeight.Bold, fontSize = 28.sp)
        Text(client.address.toString())
    }
}

@Composable
fun ClientDetailsRequisitions(
    client: Client,
    onRequisitionsListChanged: (Requisition) -> Unit,
    onRequisitionDeleted: (Requisition) -> Unit,
    onAddRequisition: (Requisition) -> Unit
) {
    Column(
        modifier = Modifier.shadow(elevation = 6.dp, shape = RoundedCornerShape(4), clip = true)
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4))
            .clip(RoundedCornerShape(4)).background(Color.White).padding(16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Requisitions", fontWeight = FontWeight.Bold, fontSize = 28.sp)

        RequisitionsTable(client,
            onRequisitionsListChanged = { requisition, _ -> onRequisitionsListChanged(requisition) },
            onRequisitionDeleted = { onRequisitionDeleted(it) },
            onAddRequisition = { onAddRequisition(it) }
        )
    }
}
