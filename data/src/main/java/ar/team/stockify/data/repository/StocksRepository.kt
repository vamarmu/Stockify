package ar.team.stockify.data.repository

import ar.team.stockify.data.source.RemoteDataSource
import ar.team.stockify.domain.Company
import ar.team.stockify.domain.Symbols

class StocksRepository(
    /*private val localDataSource: LocalDataSource,*/
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String
) {
    suspend fun getSymbols(filter: String): Symbols =
        remoteDataSource.getSymbols(filter = filter, apiKey = apiKey)

    suspend fun getDetails(filter: String): Company =
        remoteDataSource.getDetails(filter = filter, apiKey = apiKey)
}