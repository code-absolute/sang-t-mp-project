package tech.codeabsolute.presentation.new_client

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.AwtWindow
import org.koin.java.KoinJavaComponent
import tech.codeabsolute.presentation.common.ErrorText
import tech.codeabsolute.presentation.common.RequisitionsTable
import tech.codeabsolute.ui.theme.LightThemePrimary
import java.awt.FileDialog
import java.awt.Frame

@Preview
@Composable
fun NewClientSectionPreview() {

    val viewModel = object : NewClientSectionViewModel {
        override var uiState: NewClientSectionState by mutableStateOf(NewClientSectionState())
        override fun onEvent(event: NewClientSectionEvent) {}
    }

    NewClientSection(viewModel) {}
}

@Composable
fun NewClientSection(
    viewModel: NewClientSectionViewModel = KoinJavaComponent.get(NewClientSectionViewModel::class.java),
    onClientCreate: (Int?) -> Unit
) {

    val uiState = viewModel.uiState

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    ClientInfoView(uiState, viewModel)
                    ContactInfoView(uiState, viewModel)
                }
                ReferralsView(viewModel)
                AddressView(uiState, viewModel)
            }
        }
        Row(
            modifier = Modifier.height(64.dp).align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                if (uiState.hasErrors) {
                    ErrorText("Please fix errors")
                }
                Button(onClick = {
                    viewModel.onEvent(NewClientSectionEvent.Create(onClientCreate))
                }) {
                    Text("Create")
                }

            }
        }
    }
}

@Composable
fun ReferralsView(
    viewModel: NewClientSectionViewModel
) {
    Column(
        modifier = Modifier.shadow(elevation = 6.dp, shape = RoundedCornerShape(4), clip = true)
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4))
            .clip(RoundedCornerShape(4)).background(Color.White).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Requisitions", fontWeight = FontWeight.Bold, fontSize = 28.sp)

        RequisitionsTable(
            onRequisitionsListChanged = { _, list ->
                viewModel.onEvent(NewClientSectionEvent.OnRequisitionsListChanged(list))
            }, onRequisitionDeleted = {}, onAddRequisition = {})
    }
}

@Composable
fun ColumnScope.AddressView(
    state: NewClientSectionState,
    viewModel: NewClientSectionViewModel
) {
    Column(
        modifier = Modifier.shadow(elevation = 6.dp, shape = RoundedCornerShape(4), clip = true)
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4))
            .clip(RoundedCornerShape(4)).background(Color.White).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(modifier = Modifier.align(Alignment.Start), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                state.addressChecked,
                colors = CheckboxDefaults.colors(checkedColor = LightThemePrimary),
                onCheckedChange = {
                    viewModel.onEvent(NewClientSectionEvent.OnAddressCheckedChanged(it))
                })
            Text("Address (optional)", fontWeight = FontWeight.Bold, fontSize = 28.sp)
        }
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            NewClientFormField(
                value = state.addressNumber,
                error = state.addressNumberError,
                modifier = Modifier.weight(2.0f),
                labelText = "Number",
                enabled = state.addressChecked
            ) {
                viewModel.onEvent(NewClientSectionEvent.AddressNumberChanged(it))
            }
            NewClientFormField(
                value = state.addressStreet,
                error = state.addressStreetError,
                modifier = Modifier.weight(3.0f),
                labelText = "Street",
                enabled = state.addressChecked
            ) {
                viewModel.onEvent(NewClientSectionEvent.AddressStreetChanged(it))
            }
            NewClientFormField(
                value = state.addressApt,
                error = null,
                modifier = Modifier.weight(1.0f),
                labelText = "Apt (optional)",
                enabled = state.addressChecked
            ) {
                viewModel.onEvent(NewClientSectionEvent.AddressAptChanged(it))
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            NewClientFormField(
                value = state.addressCity,
                error = state.addressCityError,
                modifier = Modifier.weight(2.0f),
                labelText = "City",
                enabled = state.addressChecked
            ) {
                viewModel.onEvent(NewClientSectionEvent.AddressCityChanged(it))
            }
            NewClientFormField(
                value = state.addressProvince,
                error = state.addressProvinceError,
                modifier = Modifier.weight(1.0f),
                labelText = "Province",
                enabled = state.addressChecked
            ) {
                viewModel.onEvent(NewClientSectionEvent.AddressProvinceChanged(it))
            }

            val textFieldValue = remember {
                mutableStateOf(
                    TextFieldValue(
                        text = state.addressPostalCode, selection = TextRange(0, state.addressPostalCode.length)
                    )
                )
            }

            NewClientFormField(
                textFieldValue = textFieldValue,
                error = state.addressPostalCodeError,
                modifier = Modifier.weight(1f),
                labelText = "Postal code",
                enabled = state.addressChecked
            ) {
                var postalCode = it.text.filter { text -> !text.isWhitespace() }.uppercase()
                if (postalCode.length > 3) {
                    postalCode = StringBuilder(postalCode).insert(3, " ").toString()
                }
                viewModel.onEvent(NewClientSectionEvent.AddressPostalCodeChanged(postalCode.take(7)))
                textFieldValue.value =
                    it.copy(text = postalCode.take(7), selection = TextRange(postalCode.length))
            }
        }
    }
}

@Composable
fun RowScope.ClientInfoView(
    state: NewClientSectionState,
    viewModel: NewClientSectionViewModel
) {
    Column(
        modifier = Modifier.shadow(elevation = 6.dp, shape = RoundedCornerShape(4), clip = true)
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4))
            .clip(RoundedCornerShape(4)).background(Color.White).padding(16.dp).fillMaxWidth(0.5f),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Client Info", fontWeight = FontWeight.Bold, fontSize = 28.sp)
        ClientInfoName(state, viewModel)
        ClientInfoDateOfBirth(state, viewModel)
        ClientInfoMedicareNumber(state, viewModel)
    }
}

@Composable
private fun ClientInfoName(
    state: NewClientSectionState,
    viewModel: NewClientSectionViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        NewClientFormField(
            value = state.firstName,
            error = state.firstNameError,
            modifier = Modifier.weight(1f),
            labelText = "Given name"
        ) {
            viewModel.onEvent(NewClientSectionEvent.FirstNameChanged(it))
        }

        NewClientFormField(
            value = state.lastName,
            error = state.lastNameError,
            modifier = Modifier.weight(1f),
            labelText = "Family name"
        ) {
            viewModel.onEvent(NewClientSectionEvent.LastNameChanged(it))
        }
    }
}

@Composable
private fun ClientInfoDateOfBirth(
    state: NewClientSectionState,
    viewModel: NewClientSectionViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(50.dp)
    ) {

        val textFieldValue = remember {
            mutableStateOf(
                TextFieldValue(
                    text = state.dateOfBirth, selection = TextRange(0, state.dateOfBirth.length)
                )
            )
        }

        NewClientFormField(
            textFieldValue = textFieldValue,
            error = state.dateOfBirthError,
            modifier = Modifier.weight(1f),
            labelText = "Date of Birth (YY/MM/DD)"
        ) {
            var dateOfBirth = it.text.filter { text -> !text.isWhitespace() && text != '/' && text.isDigit() }
            if (dateOfBirth.length > 2) {
                dateOfBirth = StringBuilder(dateOfBirth).insert(2, "/").toString()
            }
            if (dateOfBirth.length > 5) {
                dateOfBirth = StringBuilder(dateOfBirth).insert(5, "/").toString()
            }
            viewModel.onEvent(NewClientSectionEvent.DateOfBirthChanged(dateOfBirth.take(8)))
            textFieldValue.value = it.copy(text = dateOfBirth.take(8), selection = TextRange(dateOfBirth.length))
        }
    }
}

@Composable
fun ClientInfoMedicareNumber(
    state: NewClientSectionState,
    viewModel: NewClientSectionViewModel
) {

    val textFieldValue = remember {
        mutableStateOf(
            TextFieldValue(
                text = state.medicareNumber, selection = TextRange(0, state.medicareNumber.length)
            )
        )
    }

    NewClientFormField(
        textFieldValue = textFieldValue,
        error = state.medicareNumberError,
        modifier = Modifier.fillMaxWidth(),
        labelText = "Medicare number"
    ) {
        var medicareNumber = it.text.filter { text -> !text.isWhitespace() }.uppercase()
        if (medicareNumber.length > 4) {
            medicareNumber = StringBuilder(medicareNumber).insert(4, " ").toString()
        }
        if (medicareNumber.length > 9) {
            medicareNumber = StringBuilder(medicareNumber).insert(9, " ").toString()
        }
        viewModel.onEvent(NewClientSectionEvent.MedicareNumberChanged(medicareNumber.take(14)))
        textFieldValue.value =
            it.copy(text = medicareNumber.take(14), selection = TextRange(medicareNumber.length))
    }
}

@Composable
fun RowScope.ContactInfoView(
    state: NewClientSectionState,
    viewModel: NewClientSectionViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.shadow(elevation = 6.dp, shape = RoundedCornerShape(4), clip = true)
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(4))
            .clip(RoundedCornerShape(4)).background(Color.White).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Contact Info", fontWeight = FontWeight.Bold, fontSize = 28.sp)
        Row(
            modifier = modifier.fillMaxWidth().wrapContentHeight()
        ) {
            val textFieldValue = remember {
                mutableStateOf(
                    TextFieldValue(
                        text = state.phoneNumber, selection = TextRange(0, state.phoneNumber.length)
                    )
                )
            }

            NewClientFormField(
                textFieldValue = textFieldValue,
                error = state.phoneNumberError,
                modifier = modifier.fillMaxWidth(),
                labelText = "Phone number"
            ) {
                var phoneNumber = it.text.filter { text -> !text.isWhitespace() && text.isDigit() }.uppercase()
                if (phoneNumber.length > 3) {
                    phoneNumber = StringBuilder(phoneNumber).insert(3, " ").toString()
                }
                if (phoneNumber.length > 7) {
                    phoneNumber = StringBuilder(phoneNumber).insert(7, " ").toString()
                }
                viewModel.onEvent(NewClientSectionEvent.PhoneNumberChanged(phoneNumber.take(12)))
                textFieldValue.value =
                    it.copy(text = phoneNumber.take(12), selection = TextRange(phoneNumber.length))
            }
        }
        Row(
            modifier = modifier.fillMaxWidth().wrapContentHeight()
        ) {
            val textFieldValue = remember {
                mutableStateOf(
                    TextFieldValue(
                        text = state.phoneNumberOther, selection = TextRange(0, state.phoneNumberOther.length)
                    )
                )
            }

            NewClientFormField(
                textFieldValue = textFieldValue,
                error = state.phoneNumberOtherError,
                modifier = modifier.fillMaxWidth(),
                labelText = "Other number (optional)"
            ) {
                var phoneNumber = it.text.filter { text -> !text.isWhitespace() && text.isDigit() }.uppercase()
                if (phoneNumber.length > 3) {
                    phoneNumber = StringBuilder(phoneNumber).insert(3, " ").toString()
                }
                if (phoneNumber.length > 7) {
                    phoneNumber = StringBuilder(phoneNumber).insert(7, " ").toString()
                }
                viewModel.onEvent(NewClientSectionEvent.PhoneNumberOtherChanged(phoneNumber.take(12)))
                textFieldValue.value =
                    it.copy(text = phoneNumber.take(12), selection = TextRange(phoneNumber.length))
            }
        }
        Row(
            modifier = modifier.fillMaxWidth().wrapContentHeight()
        ) {
            NewClientFormField(
                value = state.email,
                error = state.emailError,
                modifier = modifier.fillMaxWidth(),
                labelText = "Email (optional)"
            ) {
                viewModel.onEvent(NewClientSectionEvent.EmailChanged(it))
            }
        }
    }
}

@Composable
fun FileDialog(
    parent: Frame? = null,
    onCloseRequest: (result: String?) -> Unit
) {
    val fileDialog = object : FileDialog(parent, "Choose a file", LOAD) {
        override fun setVisible(value: Boolean) {
            super.setVisible(value)
            if (value) {
                if (directory != null && file != null) {
                    onCloseRequest(directory + file)
                } else {
                    onCloseRequest(null)
                }
            }
        }
    }
    fileDialog.setFilenameFilter { _, name ->
        name.endsWith(".pdf")
    }

    AwtWindow(
        create = { fileDialog },
        dispose = FileDialog::dispose
    )
}
