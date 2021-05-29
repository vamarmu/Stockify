package ar.team.stockify.data.repository

import ar.team.stockify.data.source.LocalDataSource
import ar.team.stockify.data.source.RemoteDataSource
import ar.team.stockify.domain.Symbols
import ar.team.stockify.domain.User

class StocksRepository(
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String
) {
    suspend fun getSymbols(filter: String): Symbols =
        remoteDataSource.getSymbols(filter = filter, apiKey = apiKey)
}