package ar.team.stockify.network

import ar.team.stockify.data.source.RemoteDataSource
import ar.team.stockify.domain.Company
import ar.team.stockify.domain.Symbols


class RemoteDataSourceImp : RemoteDataSource {
    override suspend fun getSymbols(filter: String, apiKey: String): Symbols =
        AlphaVantage.service.getSymbolSearch(
            filter,
            apiKey
        ).toSymbols()

    override suspend fun getDetails(filter: String, apiKey: String): Company =
        AlphaVantage.service.getCompanyOverview(
            filter,
            apiKey
        ).toCompany()
}