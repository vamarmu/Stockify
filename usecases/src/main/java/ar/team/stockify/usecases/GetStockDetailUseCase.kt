package ar.team.stockify.usecases

import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.domain.Stock
import ar.team.stockify.domain.StockDetail


class GetStockDetailUseCase(private val stockifyRepository: StockifyRepository) {
    suspend fun invoke(stock: String): List<StockDetail>? =
        stockifyRepository.getStockDetail(stock)
}
