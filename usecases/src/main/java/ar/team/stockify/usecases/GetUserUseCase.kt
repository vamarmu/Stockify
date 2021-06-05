package ar.team.stockify.usecases

import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.domain.User

class GetUserUseCase(private val stockifyRepository: StockifyRepository) {
    suspend fun invoke(): User? = stockifyRepository.getUser()
}
