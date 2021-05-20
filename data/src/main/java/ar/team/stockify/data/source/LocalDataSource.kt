package ar.team.stockify.data.source

import ar.team.stockify.domain.Stock
interface LocalDataSource {

    suspend fun getSymbols(): List<Stock>
}