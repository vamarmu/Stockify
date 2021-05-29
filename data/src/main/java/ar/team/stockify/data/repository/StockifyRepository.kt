package ar.team.stockify.data.repository

import ar.team.stockify.data.source.LocalDataSource
import ar.team.stockify.data.source.RemoteDataSource
import ar.team.stockify.domain.Stock
import ar.team.stockify.domain.Symbols

class StockifyRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String
) {
    suspend fun getSymbols(filter: String): Symbols =
        remoteDataSource.getSymbols(filter = filter, apiKey = apiKey)

    suspend fun getFavourites(): List<Stock> =
        localDataSource.getSymbols()
}