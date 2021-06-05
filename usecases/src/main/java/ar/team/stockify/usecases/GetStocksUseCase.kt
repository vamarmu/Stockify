package ar.team.stockify.usecases

import ar.team.stockify.data.repository.StocksRepository
import ar.team.stockify.domain.Symbols

class GetStocksUseCase(private val stocksRepository: StocksRepository) {
    suspend fun invoke(filter: String): Symbols =
        stocksRepository.getSymbols(filter)
}
