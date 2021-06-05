package ar.team.stockify.data.repository

import ar.team.stockify.data.source.LocalDataSource
import ar.team.stockify.data.source.RemoteDataSource
import ar.team.stockify.domain.Stock
import ar.team.stockify.domain.Symbols
import ar.team.stockify.domain.User

class StockifyRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String
) {
    suspend fun getSymbols(filter: String): Symbols =
        remoteDataSource.getSymbols(filter = filter, apiKey = apiKey)

    suspend fun getFavourites(): List<Stock> =
        localDataSource.getSymbols()


    suspend fun getUser() : User? = localDataSource.getUser()

suspend fun setUser(name : String, resource : String) : User = localDataSource.setUser(name, resource)

suspend fun addRemoveFavourite(stock: Stock) = localDataSource.addRemoveFavourite(stock)
}
