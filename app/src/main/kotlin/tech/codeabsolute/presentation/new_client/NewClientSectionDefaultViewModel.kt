package tech.codeabsolute.presentation.new_client

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import tech.codeabsolute.domain.model.FullName
import tech.codeabsolute.domain.model.PhoneNumber
import tech.codeabsolute.model.*
import tech.codeabsolute.use_cases.create_client.CreateClientUseCase
import tech.codeabsolute.use_cases.validation.ValidationResult
import tech.codeabsolute.use_cases.validation.ValidationUseCases
import tech.codeabsolute.util.Resource

@Single
class NewClientSectionDefaultViewModel(
    private val validationUseCases: ValidationUseCases,
    private val createClientUseCase: CreateClientUseCase
) : NewClientSectionViewModel {

    override var uiState by mutableStateOf(NewClientSectionState())

    override fun onEvent(event: NewClientSectionEvent) {
        when (event) {
            is NewClientSectionEvent.OnAddressCheckedChanged -> {
                uiState = uiState.copy(addressChecked = event.checked)
            }
            is NewClientSectionEvent.AddressAptChanged -> {
                uiState = uiState.copy(addressApt = event.apt)
            }
            is NewClientSectionEvent.AddressCityChanged -> {
                uiState = uiState.copy(addressCity = event.city)
            }
            is NewClientSectionEvent.AddressNumberChanged -> {
                uiState = uiState.copy(addressNumber = event.number)
            }
            is NewClientSectionEvent.AddressPostalCodeChanged -> {
                uiState = uiState.copy(addressPostalCode = event.postalCode)
            }
            is NewClientSectionEvent.AddressProvinceChanged -> {
                uiState = uiState.copy(addressProvince = event.province)
            }
            is NewClientSectionEvent.AddressStreetChanged -> {
                uiState = uiState.copy(addressStreet = event.street)
            }
            is NewClientSectionEvent.Create -> {
                createClient(event.onClientCreate)
            }
            is NewClientSectionEvent.EmailChanged -> {
                uiState = uiState.copy(email = event.email)
            }
            is NewClientSectionEvent.FirstNameChanged -> {
                uiState = uiState.copy(firstName = event.firstName)
            }
            is NewClientSectionEvent.LastNameChanged -> {
                uiState = uiState.copy(lastName = event.lastName)
            }
            is NewClientSectionEvent.MedicareNumberChanged -> {
                uiState = uiState.copy(medicareNumber = event.medicareNumber)
            }
            is NewClientSectionEvent.PhoneNumberChanged -> {
                uiState = uiState.copy(phoneNumber = event.phoneNumber)
            }
            is NewClientSectionEvent.PhoneNumberOtherChanged -> {
                uiState = uiState.copy(phoneNumberOther = event.phoneNumber)
            }
            is NewClientSectionEvent.DateOfBirthChanged -> {
                uiState = uiState.copy(dateOfBirth = event.dateOfBirth)
            }
            is NewClientSectionEvent.OnRequisitionsListChanged -> {
                uiState = uiState.copy(requisitions = event.requisitions)
            }
        }
    }

    private fun createClient(onClientCreate: (Int) -> Unit) {
        val firstNameResult = validationUseCases.validFirstNameUseCase(uiState.firstName)
        val lastNameResult = validationUseCases.validLastNameUseCase(uiState.lastName)
        val dateOfBirthResult = validationUseCases.validDateOfBirthUseCase(uiState.dateOfBirth)
        val medicareNumberResult = validationUseCases.validMedicareNumberUseCase(uiState.medicareNumber)
        val phoneNumberResult = validationUseCases.validPhoneNumberUseCase(uiState.phoneNumber)
        val phoneNumberOtherResult = validationUseCases.validPhoneNumberOtherUseCase(uiState.phoneNumberOther)
        val emailResult = validationUseCases.validEmailUseCase(uiState.email)

        var addressNumberResult: ValidationResult? = null
        var addressStreetResult: ValidationResult? = null
        var addressCityResult: ValidationResult? = null
        var addressProvinceResult: ValidationResult? = null
        var addressPostalCodeResult: ValidationResult? = null

        if (uiState.addressChecked) {
            addressNumberResult = validationUseCases.validAddressNumberUseCase(uiState.addressNumber)
            addressStreetResult = validationUseCases.validAddressStreetUseCase(uiState.addressStreet)
            addressCityResult = validationUseCases.validAddressCityUseCase(uiState.addressCity)
            addressProvinceResult = validationUseCases.validAddressProvinceUseCase(uiState.addressProvince)
            addressPostalCodeResult = validationUseCases.validAddressPostalCodeUseCase(uiState.addressPostalCode)
        }

        val results = mutableListOf(
            firstNameResult,
            lastNameResult,
            dateOfBirthResult,
            medicareNumberResult,
            phoneNumberResult,
            emailResult,
            addressNumberResult,
            addressStreetResult,
            addressCityResult,
            addressProvinceResult,
            addressPostalCodeResult
        )

        if (results.filterNotNull().any { !it.successful }) {
            uiState = uiState.copy(
                firstNameError = firstNameResult.errorMessage,
                lastNameError = lastNameResult.errorMessage,
                dateOfBirthError = dateOfBirthResult.errorMessage,
                medicareNumberError = medicareNumberResult.errorMessage,
                phoneNumberError = phoneNumberResult.errorMessage,
                phoneNumberOtherError = phoneNumberOtherResult.errorMessage,
                emailError = emailResult.errorMessage,
                addressNumberError = addressNumberResult?.errorMessage,
                addressStreetError = addressStreetResult?.errorMessage,
                addressCityError = addressCityResult?.errorMessage,
                addressProvinceError = addressProvinceResult?.errorMessage,
                addressPostalCodeError = addressPostalCodeResult?.errorMessage,
                hasErrors = true
            )
        } else {
            uiState = uiState.copy(hasErrors = false)
            create(onClientCreate)
        }
    }

    private fun create(onClientCreate: (Int) -> Unit) {
        val fullName = FullName(
            firstName = uiState.firstName,
            lastName = uiState.lastName
        )

        val dateOfBirth = uiState.dateOfBirth.toDateOfBirth()

        val contactInfo = ContactInfo(
            phoneNumber = PhoneNumber(uiState.phoneNumber.filter { !it.isWhitespace() }),
            otherNumber = PhoneNumber(uiState.phoneNumberOther.filter { !it.isWhitespace() }),
            emailAddress = EmailAddress(uiState.email)
        )

        val address = if (uiState.addressChecked) {
            Address(
                number = uiState.addressNumber,
                street = uiState.addressStreet,
                city = uiState.addressCity,
                postalCode = uiState.addressPostalCode.filter { !it.isWhitespace() },
                apartment = uiState.addressApt,
                province = uiState.addressProvince,
            )
        } else {
            null
        }

        val client = Client(
            fullName = fullName,
            dateOfBirth = dateOfBirth,
            medicareNumber = MedicareNumber(uiState.medicareNumber.filter { !it.isWhitespace() }),
            contactInfo = contactInfo,
            address = address,
            requisitions = uiState.requisitions
        )
        CoroutineScope(Dispatchers.Main).launch {
            createClientUseCase(client).collect {
                uiState = when (it) {
                    is Resource.Error -> uiState.copy(isLoading = false)
                    is Resource.Loading -> uiState.copy(isLoading = true)
                    is Resource.Success -> {
                        it.data?.id?.let { clientId -> onClientCreate(clientId) }
                        NewClientSectionState()
                    }
                }
            }
        }
    }
}