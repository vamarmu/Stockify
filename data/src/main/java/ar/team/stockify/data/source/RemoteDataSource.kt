package ar.team.stockify.data.source

import ar.team.stockify.domain.Stock

interface RemoteDataSource {

    suspend fun getStocks(filter: String, apiKey: String): List<Stock>

}