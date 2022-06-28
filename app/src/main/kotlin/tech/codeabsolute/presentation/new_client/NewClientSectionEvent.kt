package tech.codeabsolute.presentation.new_client

import tech.codeabsolute.model.Requisition

sealed class NewClientSectionEvent {
    data class FirstNameChanged(val firstName: String) : NewClientSectionEvent()
    data class LastNameChanged(val lastName: String) : NewClientSectionEvent()
    data class DateOfBirthChanged(val dateOfBirth: String) : NewClientSectionEvent()
    data class MedicareNumberChanged(val medicareNumber: String) : NewClientSectionEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : NewClientSectionEvent()
    data class PhoneNumberOtherChanged(val phoneNumber: String) : NewClientSectionEvent()
    data class EmailChanged(val email: String) : NewClientSectionEvent()
    data class OnAddressCheckedChanged(val checked: Boolean) : NewClientSectionEvent()
    data class AddressNumberChanged(val number: String) : NewClientSectionEvent()
    data class AddressStreetChanged(val street: String) : NewClientSectionEvent()
    data class AddressAptChanged(val apt: String) : NewClientSectionEvent()
    data class AddressCityChanged(val city: String) : NewClientSectionEvent()
    data class AddressProvinceChanged(val province: String) : NewClientSectionEvent()
    data class AddressPostalCodeChanged(val postalCode: String) : NewClientSectionEvent()
    data class OnRequisitionsListChanged(val requisitions: List<Requisition>) : NewClientSectionEvent()
    data class Create(val onClientCreate: (Int) -> Unit) : NewClientSectionEvent()
    object OpenFileChooser : NewClientSectionEvent()
    object CloseFileChooser : NewClientSectionEvent()
}

