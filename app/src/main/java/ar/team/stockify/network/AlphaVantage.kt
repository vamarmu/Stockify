package ar.team.stockify.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AlphaVantage {

    private const val BASE_URL = "https://www.alphavantage.co"

    private val okHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
                .addInterceptor(this)
                .build()
    }

    val service: ApiAlphaVantage = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .run {
                create(ApiAlphaVantage::class.java)
            }
}


object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
}
