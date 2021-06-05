package ar.team.stockify.network

import ar.team.stockify.network.model.RemoteCompany
import ar.team.stockify.network.model.RemoteListBestMatches
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiAlphaVantage {

    companion object {
        const val COMPANY_OVERVIEW = "/api/simulation/restaurantsByGeo"
        const val SYMBOL_SEARCH_FUNCTION = "SYMBOL_SEARCH"
    }

    @GET("/query?function=SYMBOL_SEARCH")
    suspend fun getStocks(
        @Query("keywords") keywords: String,
        @Query("apikey") apiKey: String
    ): RemoteListBestMatches

    @GET("/query?function=EARNINGS")
    suspend fun getCompanyOverview(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): RemoteCompany
}