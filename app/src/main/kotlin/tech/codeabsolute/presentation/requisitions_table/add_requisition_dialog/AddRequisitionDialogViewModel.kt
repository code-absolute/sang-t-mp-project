package tech.codeabsolute.presentation.requisitions_table.add_requisition_dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import tech.codeabsolute.model.Requisition
import tech.codeabsolute.use_cases.get_requisition_types.GetRequisitionTypesUseCase
import tech.codeabsolute.util.empty

class AddRequisitionDialogViewModel(
    private val getRequisitionTypesUseCase: GetRequisitionTypesUseCase
) {

    var uiState by mutableStateOf(
        AddRequisitionDialogState(
            displayedRequisitionPath = "No file selected"
        )
    )

    var existingRequisitionId: Int? = null

    init {
        uiState = uiState.copy(isLoading = true)
    }

    fun onEvent(event: AddRequisitionDialogEvent) {
        when (event) {
            is AddRequisitionDialogEvent.InitDialog -> {
                uiState = uiState.copy(isLoading = true)
                CoroutineScope(Dispatchers.IO).launch {
                    val types = getRequisitionTypesUseCase()
                    withContext(Dispatchers.Main) {

                        val type = if (event.requisition != null) {
                            setFieldValues(event.requisition)
                            existingRequisitionId = event.requisition.id
                            types.first { it.id == event.requisition.typeId }
                        } else {
                            types.first()
                        }

                        uiState = uiState.copy(
                            isLoading = false,
                            requisitionTypes = types,
                            requisitionType = type
                        )
                    }
                }
            }
            AddRequisitionDialogEvent.GetRequisitionTypes -> {
                uiState = uiState.copy(isLoading = true)
                CoroutineScope(Dispatchers.IO).launch {
                    val types = getRequisitionTypesUseCase()
                    withContext(Dispatchers.Main) {
                        uiState = uiState.copy(
                            isLoading = false,
                            requisitionTypes = types,
                            requisitionType = types[0]
                        )
                    }
                }
            }
            is AddRequisitionDialogEvent.OnAddClicked -> {
                event.onAddRequisition(buildRequisition())
                uiState = uiState.copy(
                    requisitionPath = String.empty(),
                    displayedRequisitionPath = "No file selected",
                    requisitionType = uiState.requisitionTypes[0],
                    isTested = false,
                    testDateTextFieldValue = TextFieldValue(),
                    testTimeTextFieldValue = TextFieldValue(),
                    testAMPM = "AM"
                )
            }
            is AddRequisitionDialogEvent.OnFileSelected -> {
                uiState = uiState.copy(
                    requisitionPath = event.path,
                    displayedRequisitionPath = event.path.substringAfterLast('/')
                )
            }
            is AddRequisitionDialogEvent.OnTypeChanged -> {
                uiState = uiState.copy(
                    requisitionType = event.type
                )
            }
            is AddRequisitionDialogEvent.OnIsTestedCheckChanged -> {
                uiState = uiState.copy(
                    isTested = event.checked
                )
            }
            is AddRequisitionDialogEvent.OnTestDateChanged -> onTestDateChanged(event.date)
            is AddRequisitionDialogEvent.OnTestTimeChanged -> onTestTimeChanged(event.time)
            is AddRequisitionDialogEvent.OnTestAMPMChanged -> {
                uiState = uiState.copy(
                    testAMPM = event.value
                )
            }
        }
    }

    private fun buildRequisition(): Requisition {

        val testedOn = if (uiState.isTested) {
            DateTime(
                uiState.testDateTextFieldValue.text.substringBefore('/').toInt(),
                uiState.testDateTextFieldValue.text.substringAfter('/').substringBefore('/').toInt(),
                uiState.testDateTextFieldValue.text.substringAfterLast('/').toInt(),
                uiState.testTimeTextFieldValue.text.substringBefore(':').toInt(),
                uiState.testTimeTextFieldValue.text.substringAfter(':').toInt()
            )
        } else {
            null
        }

        return Requisition(
            id = existingRequisitionId ?: -1,
            path = uiState.requisitionPath,
            typeId = uiState.requisitionType.id,
            typeName = uiState.requisitionType.name,
            testedOn = testedOn
        )
    }

    private fun onTestDateChanged(date: String) {

        var s = date.filter { text -> !text.isWhitespace() && text != '/' && text.isDigit() }
        if (s.length > 4) {
            s = StringBuilder(s).insert(4, "/").toString()
        }
        if (s.length > 7) {
            s = StringBuilder(s).insert(7, "/").toString()
        }

        uiState = uiState.copy(
            testDateTextFieldValue = uiState.testDateTextFieldValue.copy(
                text = s.take(10), selection = TextRange(s.length)
            )
        )
    }

    private fun onTestTimeChanged(time: String) {

        var s = time.filter { text -> !text.isWhitespace() && text != ':' && text.isDigit() }
        if (s.length > 2) {
            s = StringBuilder(s).insert(2, ":").toString()
        }

        uiState = uiState.copy(
            testTimeTextFieldValue = uiState.testTimeTextFieldValue.copy(
                text = s.take(5), selection = TextRange(s.length)
            )
        )
    }

    private fun setFieldValues(requisition: Requisition) {

        val types = uiState.requisitionTypes

        val testDate = requisition.testedOn?.let {
            val dateFormat = DateTimeFormat.forPattern("YYYY/MM/dd")
            dateFormat.print(it.toLocalDate())
        } ?: String.empty()

        val testTime = requisition.testedOn?.let {
            val timeFormat = DateTimeFormat.forPattern("HH:mm")
            timeFormat.print(it.toLocalTime())
        } ?: String.empty()

        onEvent(AddRequisitionDialogEvent.OnFileSelected(requisition.path))
        onEvent(AddRequisitionDialogEvent.OnTypeChanged(types.firstOrNull { it.id == requisition.typeId } ?: types[0]))
        onEvent(AddRequisitionDialogEvent.OnIsTestedCheckChanged(testDate.isNotEmpty()))
        onEvent(AddRequisitionDialogEvent.OnTestDateChanged(testDate))
        onEvent(AddRequisitionDialogEvent.OnTestTimeChanged(testTime))
    }
}