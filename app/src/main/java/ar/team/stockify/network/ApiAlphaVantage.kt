package ar.team.stockify.network

import ar.team.stockify.model.Company
import ar.team.stockify.model.Symbol
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiAlphaVantage {

    companion object {
        const val COMPANY_OVERVIEW = "/api/simulation/restaurantsByGeo"
    }

    @GET("/query")
    suspend fun getSymbolSearch(
        @Query("function") function: String,
        @Query("keywords") keywords: String,
        @Query("apikey") apiKey: String
    ): Symbol

    @GET("/query")
    suspend fun getCompanyOverview(
        @Query("function") function: String,
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): Company
}