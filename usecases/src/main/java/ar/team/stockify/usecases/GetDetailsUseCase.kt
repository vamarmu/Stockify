package ar.team.stockify.usecases

import ar.team.stockify.data.repository.StocksRepository
import ar.team.stockify.domain.Company

class GetDetailsUseCase(private val stocksRepository: StocksRepository) {
    suspend fun invoke(filter: String): Company =
        stocksRepository.getDetails(filter)
}