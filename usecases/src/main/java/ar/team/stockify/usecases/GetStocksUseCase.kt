package ar.team.stockify.usecases

import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.domain.Symbols

class GetStocksUseCase(private val stockifyRepository: StockifyRepository) {
    suspend fun invoke(filter: String): Symbols =
        stockifyRepository.getSymbols(filter)
}
