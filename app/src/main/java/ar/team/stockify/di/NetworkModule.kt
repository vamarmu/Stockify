package ar.team.stockify.di

import ar.team.stockify.network.AlphaVantage
import ar.team.stockify.network.ApiAlphaVantage
import ar.team.stockify.network.Keys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(this)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiAlphaVantage(
        endpoint: String,
        okClient: OkHttpClient
    ): ApiAlphaVantage {
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .client(okClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .run {
                create(ApiAlphaVantage::class.java)
            }
    }

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(): String = Keys.apiKey()
}