package ar.team.stockify.usecases

import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.domain.Stock

class AddRemoveFavUseCase (private val stockifyRepository: StockifyRepository) {
    suspend fun invoke(stock: Stock) = stockifyRepository.addRemoveFavourite(stock)
}