package ar.team.stockify.data.repository

import ar.team.stockify.data.source.LocalDataSource
import ar.team.stockify.domain.Stock
import ar.team.stockify.domain.Symbols

class FavouritesRepository(
    private val localDataSource: LocalDataSource,
) {
    suspend fun getSymbols(): List<Stock> =
        localDataSource.getSymbols()
}