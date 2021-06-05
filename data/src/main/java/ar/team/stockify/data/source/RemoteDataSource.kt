package ar.team.stockify.data.source

import ar.team.stockify.domain.Symbols

interface RemoteDataSource {
    suspend fun getSymbols(filter: String, apiKey: String): Symbols
}