package ar.team.stockify.network

import ar.team.stockify.data.source.RemoteDataSource
import ar.team.stockify.domain.Stock
import ar.team.stockify.domain.StockDetail


class RemoteDataSourceImp : RemoteDataSource {

    override suspend fun getStocks(filter: String, apiKey: String): List<Stock> =
        AlphaVantage.service.getStocks(
            filter,
            apiKey
        ).bestMatches.map {
            it.toStock()
        }

    override suspend fun getStockDetail(filter: String, apiKey: String): List<StockDetail>? {
        val rest = AlphaVantage.service.getStockDetail(filter,apiKey)

        return rest.toListStockDetail()
    }


}

