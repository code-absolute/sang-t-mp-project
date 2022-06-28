package tech.codeabsolute.model


import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName("Active")
    val active: Boolean = false,
    @SerializedName("Balance")
    val balance: Double = 0.0,
    @SerializedName("BalanceWithJobs")
    val balanceWithJobs: Double = 0.0,
    @SerializedName("BillAddr")
    val billAddr: BillAddr = BillAddr(),
    @SerializedName("BillWithParent")
    val billWithParent: Boolean = false,
    @SerializedName("ClientEntityId")
    val clientEntityId: String = "",
    @SerializedName("CompanyName")
    val companyName: String = "",
    @SerializedName("CurrencyRef")
    val currencyRef: CurrencyRef = CurrencyRef(),
    @SerializedName("DisplayName")
    val displayName: String = "",
    @SerializedName("domain")
    val domain: String = "",
    @SerializedName("FamilyName")
    val familyName: String = "",
    @SerializedName("Fax")
    val fax: Fax = Fax(),
    @SerializedName("FullyQualifiedName")
    val fullyQualifiedName: String = "",
    @SerializedName("GivenName")
    val givenName: String = "",
    @SerializedName("Id")
    val id: String = "",
    @SerializedName("IsProject")
    val isProject: Boolean = false,
    @SerializedName("Job")
    val job: Boolean = false,
    @SerializedName("Level")
    val level: Int = 0,
    @SerializedName("MetaData")
    val metaData: MetaData = MetaData(),
    @SerializedName("MiddleName")
    val middleName: String = "",
    @SerializedName("Mobile")
    val mobile: Mobile = Mobile(),
    @SerializedName("ParentRef")
    val parentRef: ParentRef = ParentRef(),
    @SerializedName("PreferredDeliveryMethod")
    val preferredDeliveryMethod: String = "",
    @SerializedName("PrimaryEmailAddr")
    val primaryEmailAddr: PrimaryEmailAddr = PrimaryEmailAddr(),
    @SerializedName("PrimaryPhone")
    val primaryPhone: PrimaryPhone = PrimaryPhone(),
    @SerializedName("PrintOnCheckName")
    val printOnCheckName: String = "",
    @SerializedName("ShipAddr")
    val shipAddr: ShipAddr = ShipAddr(),
    @SerializedName("sparse")
    val sparse: Boolean = false,
    @SerializedName("SyncToken")
    val syncToken: String = "",
    @SerializedName("Taxable")
    val taxable: Boolean = false,
    @SerializedName("V4IDPseudonym")
    val v4IDPseudonym: String = "",
    @SerializedName("WebAddr")
    val webAddr: WebAddr = WebAddr()
)