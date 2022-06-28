package tech.codeabsolute.data

import retrofit2.Response
import retrofit2.http.*
import tech.codeabsolute.model.*
import tech.codeabsolute.model.invoice_item.QuickbooksInvoiceItemsQueryResponse

interface QuickbooksService {

    @Headers("Accept: application/json")
    @GET("/v3/company/{realmId}/query")
    suspend fun queryCustomer(
        @Header("Authorization") authHeader: String,
        @Path("realmId") realmId: String?,
        @Query("query") query: String
    ): Response<QuickbooksQueryResponse>

    @Headers("Accept: application/json")
    @POST("/v3/company/{realmId}/customer")
    suspend fun createCustomer(
        @Header("Authorization") authHeader: String,
        @Path("realmId") realmId: String?,
        @Body customerRequest: CustomerRequest
    ): Response<QuickbooksCustomerResponse>

    @Headers("Accept: application/json")
    @GET("/v3/company/{realmId}/query")
    suspend fun queryCustomers(
        @Header("Authorization") authHeader: String,
        @Path("realmId") realmId: String?,
        @Query("query") query: String
    ): Response<QuickbooksQueryResponse>

    @Headers("Accept: application/json")
    @POST("/v3/company/{realmId}/invoice")
    suspend fun createInvoice(
        @Header("Authorization") authHeader: String,
        @Path("realmId") realmId: String?,
        @Body invoiceRequest: InvoiceRequest
    ): Response<QuickbooksInvoiceResponse>

    @Headers("Accept: application/json")
    @GET("/v3/company/{realmId}/query")
    suspend fun getInvoiceItems(
        @Header("Authorization") authHeader: String,
        @Path("realmId") realmId: String?,
        @Query("query") query: String
    ): Response<QuickbooksInvoiceItemsQueryResponse>

    @Headers("Accept: application/json")
    @GET("/v3/company/{realmId}/query")
    suspend fun getParentRef(
        @Header("Authorization") authHeader: String,
        @Path("realmId") realmId: String?,
        @Query("query") query: String
    ): Response<QuickbooksInvoiceItemsQueryResponse>

    companion object {
        // const val BASE_URL = "https://sandbox-quickbooks.api.intuit.com" // Dev
        const val BASE_URL = "https://quickbooks.api.intuit.com/"
    }
}