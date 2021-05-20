package ar.team.stockify.usecases

import ar.team.stockify.data.repository.FavouritesRepository
import ar.team.stockify.data.repository.StocksRepository
import ar.team.stockify.domain.Stock
import ar.team.stockify.domain.Symbols

class GetFavouritesUseCase(private val favouritesRepository: FavouritesRepository) {
    suspend fun invoke(): List<Stock> =
        favouritesRepository.getSymbols()
}
