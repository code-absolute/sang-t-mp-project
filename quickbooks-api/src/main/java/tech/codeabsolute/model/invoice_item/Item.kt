package tech.codeabsolute.model.invoice_item


import com.google.gson.annotations.SerializedName
import tech.codeabsolute.util.empty

data class Item(
    @SerializedName("Name")
    var name: String = String.empty(),
    @SerializedName("Description")
    var description: String = String.empty(),
    @SerializedName("Active")
    var active: Boolean? = null,
    @SerializedName("SubItem")
    var subItem: Boolean? = null,
    @SerializedName("ParentRef")
    var parentRef: ParentRef? = ParentRef(),
    @SerializedName("Level")
    var level: Int? = null,
    @SerializedName("FullyQualifiedName")
    var fullyQualifiedName: String? = null,
    @SerializedName("Taxable")
    var taxable: Boolean? = null,
    @SerializedName("SalesTaxIncluded")
    var salesTaxIncluded: Boolean? = null,
    @SerializedName("UnitPrice")
    var unitPrice: Double = 0.0,
    @SerializedName("Type")
    var type: String? = null,
    @SerializedName("IncomeAccountRef")
    var incomeAccountRef: IncomeAccountRef? = IncomeAccountRef(),
    @SerializedName("PurchaseTaxIncluded")
    var purchaseTaxIncluded: Boolean? = null,
    @SerializedName("PurchaseCost")
    var purchaseCost: Int? = null,
    @SerializedName("TrackQtyOnHand")
    var trackQtyOnHand: Boolean? = null,
    @SerializedName("SalesTaxCodeRef")
    var salesTaxCodeRef: SalesTaxCodeRef = SalesTaxCodeRef(),
    @SerializedName("domain")
    var domain: String? = null,
    @SerializedName("sparse")
    var sparse: Boolean? = null,
    @SerializedName("Id")
    var id: String = String.empty(),
    @SerializedName("SyncToken")
    var syncToken: String? = null,
    @SerializedName("MetaData")
    var metaData: MetaData? = MetaData()
)