package tech.codeabsolute.di

//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.mockkStatic
//import io.mockk.verifyOrder
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import tech.codeabsolute.data.QuickbooksService
//
//class QuickbooksKoinModuleTest {
//
//    private lateinit var module: QuickbooksModule
//
//    @BeforeEach
//    fun setUp() {
//        module = QuickbooksKoinModule()
//    }
//
//    @Test
//    fun `Provide QuickbooksService`() {
//
//        val baseUrl = QuickbooksService.BASE_URL
//        val serviceClass = QuickbooksService::class.java
//        val retrofitBuilder = mockk<Retrofit.Builder>()
//        val retrofit = mockk<Retrofit>()
//        val expectedService = mockk<QuickbooksService>()
//        every { retrofitBuilder.baseUrl(baseUrl) } returns retrofitBuilder
//        every { retrofitBuilder.build() } returns retrofit
//        every { retrofit.create(serviceClass) } returns expectedService
//
//        val service = module.provideQuickbooksService(retrofitBuilder)
//
//        verifyOrder {
//            retrofitBuilder.baseUrl(baseUrl)
//            retrofitBuilder.build()
//            retrofit.create(serviceClass)
//        }
//        assertEquals(expectedService, service)
//    }
//
//    @Test
//    fun `Provide HttpLoggingInterceptor with logging level BODY`() {
//
//        val expectedLoggingLevel = HttpLoggingInterceptor.Level.BODY
//
//        val interceptor = module.provideHttpInterceptor()
//
//        assertEquals(expectedLoggingLevel, interceptor.level)
//    }
//
//    @Test
//    fun `Provide OkHttpClient with HttpLoggingInterceptor`() {
//
//        val builder = mockk<OkHttpClient.Builder>()
//        val httpLoggingInterceptor = mockk<HttpLoggingInterceptor>()
//        val expectedOkHttpClient = mockk<OkHttpClient>()
//        every { builder.addInterceptor(httpLoggingInterceptor) } returns builder
//        every { builder.build() } returns expectedOkHttpClient
//
//        val okHttpClient = module.provideOkHttpClient(builder, httpLoggingInterceptor)
//
//        verifyOrder {
//            builder.addInterceptor(httpLoggingInterceptor)
//            builder.build()
//        }
//        assertEquals(expectedOkHttpClient, okHttpClient)
//    }
//
//    @Test
//    fun `Provide Gson`() {
//
//        val gsonBuilder = mockk<GsonBuilder>()
//        val expectedGson = mockk<Gson>()
//        every { gsonBuilder.setPrettyPrinting() } returns gsonBuilder
//        every { gsonBuilder.serializeNulls() } returns gsonBuilder
//        every { gsonBuilder.create() } returns expectedGson
//
//        val gson = module.provideGson(gsonBuilder)
//
//        verifyOrder {
//            gsonBuilder.setPrettyPrinting()
//            gsonBuilder.serializeNulls()
//            gsonBuilder.create()
//        }
//        assertEquals(expectedGson, gson)
//    }
//
//    @Test
//    fun `Provide GsonConverterFactory with provided Gson`() {
//
//        val gson = mockk<Gson>()
//        val expectedGsonConverterFactory = mockk<GsonConverterFactory>()
//        mockkStatic(GsonConverterFactory::class)
//        every { GsonConverterFactory.create(gson) } returns expectedGsonConverterFactory
//
//        val gsonConverterFactory = module.provideGsonConverterFactory(gson)
//
//        assertEquals(expectedGsonConverterFactory, gsonConverterFactory)
//    }
//
//    @Test
//    fun `Provide Retrofit Builder with provided OkHttpClient and converter factories`() {
//
//        val expectedRetrofitBuilder = mockk<Retrofit.Builder>()
//        val okHttpClient = mockk<OkHttpClient>()
//        val gsonConverterFactory = mockk<GsonConverterFactory>()
//        every { expectedRetrofitBuilder.client(okHttpClient) } returns expectedRetrofitBuilder
//        every { expectedRetrofitBuilder.addConverterFactory(gsonConverterFactory) } returns expectedRetrofitBuilder
//
//        val retrofitBuilder = module.provideRetrofitBuilder(expectedRetrofitBuilder, okHttpClient, gsonConverterFactory)
//
//        verifyOrder {
//            retrofitBuilder.client(okHttpClient)
//            retrofitBuilder.addConverterFactory(gsonConverterFactory)
//        }
//        assertEquals(expectedRetrofitBuilder, retrofitBuilder)
//    }
//}