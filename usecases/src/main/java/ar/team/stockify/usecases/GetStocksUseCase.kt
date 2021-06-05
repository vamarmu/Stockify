package ar.team.stockify.usecases

import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.domain.Stock


class GetStocksUseCase(private val stockifyRepository: StockifyRepository) {
    suspend fun invoke(filter: String): List<Stock> =
        stockifyRepository.getStocks(filter)
}
