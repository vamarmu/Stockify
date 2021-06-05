package ar.team.stockify.network

import ar.team.stockify.model.RemoteSymbol
import ar.team.stockify.network.model.RemoteCompany
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiAlphaVantage {

    companion object {
        const val COMPANY_OVERVIEW = "/api/simulation/restaurantsByGeo"
        const val SYMBOL_SEARCH_FUNCTION = "SYMBOL_SEARCH"
    }

    @GET("/query?function=SYMBOL_SEARCH")
    suspend fun getSymbolSearch(
        @Query("keywords") keywords: String,
        @Query("apikey") apiKey: String
    ): RemoteSymbol

    @GET("/query?function=EARNINGS")
    suspend fun getCompanyOverview(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): RemoteCompany
}