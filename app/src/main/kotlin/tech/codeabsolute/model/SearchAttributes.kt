package tech.codeabsolute.model

data class SearchAttributes(
    val searchTerm: String,
    val filter: Filter,
    val order: Order
) {
    enum class Filter(val text: String) {
        MEDICARE_NUMBER("Medicare Number"),
        NAME("Name")
    }

    enum class Order(val text: String) {
        ASCENDING("Ascending"),
        DESCENDING("Descending")
    }
}