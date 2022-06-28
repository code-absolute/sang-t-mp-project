package tech.codeabsolute.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.intuit.oauth2.client.OAuth2PlatformClient
import com.intuit.oauth2.config.Environment
import com.intuit.oauth2.config.OAuth2Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.codeabsolute.client.QuickbooksOAuth2Client
import tech.codeabsolute.data.QuickbooksDataSource
import tech.codeabsolute.data.QuickbooksPublicDataSource
import tech.codeabsolute.data.QuickbooksService
import tech.codeabsolute.util.AppPreferences
import tech.codeabsolute.util.QuickbooksAppPreferences
import tech.codeabsolute.util.URIUtils
import java.util.prefs.Preferences

@Module
class QuickbooksKoinModule : QuickbooksModule {

    @Single
    fun provideQuickbooksDataSource(
        quickbooksService: QuickbooksService,
        appPreferences: AppPreferences
    ): QuickbooksDataSource =
        QuickbooksPublicDataSource(quickbooksService, appPreferences)

    @Single
    override fun provideQuickbooksService(
        @Named("ConstructedRetrofitBuilder")
        retrofitBuilder: Retrofit.Builder
    ): QuickbooksService =
        retrofitBuilder
            .baseUrl(QuickbooksService.BASE_URL)
            .build()
            .create(QuickbooksService::class.java)

    @Single
    @Named("ConstructedRetrofitBuilder")
    override fun provideRetrofitBuilder(
        @Named("DefaultRetrofitBuilder")
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit.Builder = retrofitBuilder
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)

    @Single
    @Named("DefaultRetrofitBuilder")
    fun provideDefaultRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    @Single
    override fun provideOkHttpClient(
        okHttpClientBuilder: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = okHttpClientBuilder
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Single
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Single
    override fun provideHttpInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Single
    override fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Single
    override fun provideGson(gsonBuilder: GsonBuilder): Gson = gsonBuilder
        .setPrettyPrinting()
        .serializeNulls()
        .create()

    @Single
    fun providerGsonBuilder(): GsonBuilder = GsonBuilder()

    @Single
    fun provideQuickbooksOAuth2Client(
        oAuth2Config: OAuth2Config,
        oAuth2PlatformClient: OAuth2PlatformClient,
        appPreferences: AppPreferences,
        uriUtils: URIUtils
    ): QuickbooksOAuth2Client =
        QuickbooksOAuth2Client(oAuth2Config, oAuth2PlatformClient, appPreferences, uriUtils)

    @Single
    fun provideOAuth2Config(): OAuth2Config = OAuth2Config
        .OAuth2ConfigBuilder(
            OAuth2AppClientId,
            OAuth2AppClientSecret
        ).callDiscoveryAPI(Environment.SANDBOX).buildConfig()

    @Single
    fun provideOAuth2PlatformClient(oAuth2Config: OAuth2Config): OAuth2PlatformClient =
        OAuth2PlatformClient(oAuth2Config)

    @Single
    fun provideQuickbooksAppPreferences(preferences: Preferences): AppPreferences =
        QuickbooksAppPreferences(preferences)

    @Single
    fun providePreferences(): Preferences = Preferences.userRoot().node("sang-t-mp")

    companion object {
        // const val OAuth2AppClientId = "ABgFKV0rzKvful97t72edeGrH0aHEJqNjH9BOjjb7BK85bDYux" // Dev
        // const val OAuth2AppClientSecret = "0Jj3usuHYIKQRLHjWrSmuuKkpkJyEMUHIr2WgCWE" // Dev
        const val OAuth2AppClientId = "ABA7Xh77bY8Mn8sAXObOnKcWGqsGHyoIIky2qJOWcUEsIXlbCh" // Prod
        const val OAuth2AppClientSecret = "sU712bsrVmck1I7ejAbG3FcbxC94SdRQzlcsjflW" // Prod


    }
}