package ar.team.stockify.usecases

import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.domain.Stock

class GetFavouritesUseCase(private val stockifyRepository: StockifyRepository) {
    suspend fun invoke(): List<Stock> =
        stockifyRepository.getFavourites()
}
