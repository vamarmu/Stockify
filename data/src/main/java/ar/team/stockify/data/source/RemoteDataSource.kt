package ar.team.stockify.data.source

import ar.team.stockify.domain.Stock
import ar.team.stockify.domain.StockDetail

interface RemoteDataSource {

    suspend fun getStocks(filter: String, apiKey: String): List<Stock>

    suspend fun getStockDetail(filter: String, apiKey: String): List<StockDetail>?



}