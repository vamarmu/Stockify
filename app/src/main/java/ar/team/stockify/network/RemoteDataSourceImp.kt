package ar.team.stockify.network

import ar.team.stockify.data.source.RemoteDataSource
import ar.team.stockify.domain.Stock


class RemoteDataSourceImp : RemoteDataSource {

    override suspend fun getStocks(filter: String, apiKey: String): List<Stock> =
        AlphaVantage.service.getStocks(
            filter,
            apiKey
        ).bestMatches.map {
            it.toStock()
        }
}

