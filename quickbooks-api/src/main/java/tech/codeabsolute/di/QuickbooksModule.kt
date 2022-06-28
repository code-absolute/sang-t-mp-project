package tech.codeabsolute.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.codeabsolute.data.QuickbooksService

interface QuickbooksModule {

    fun provideQuickbooksService(retrofitBuilder: Retrofit.Builder): QuickbooksService
    fun provideRetrofitBuilder(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit.Builder

    fun provideOkHttpClient(
        okHttpClientBuilder: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient

    fun provideHttpInterceptor(): HttpLoggingInterceptor
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory
    fun provideGson(gsonBuilder: GsonBuilder): Gson
}