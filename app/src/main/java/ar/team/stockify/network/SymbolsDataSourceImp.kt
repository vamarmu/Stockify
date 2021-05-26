package ar.team.stockify.network

import ar.team.stockify.data.source.RemoteDataSource
import ar.team.stockify.domain.Symbols


class SymbolsDataSourceImp : RemoteDataSource {
    override suspend fun getSymbols(filter: String, apiKey: String): Symbols =
        AlphaVantage.service.getSymbolSearch(
            filter,
            apiKey
        ).toSymbols()
}