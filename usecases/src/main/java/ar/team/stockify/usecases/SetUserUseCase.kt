package ar.team.stockify.usecases

import ar.team.stockify.data.repository.StockifyRepository
import ar.team.stockify.domain.Symbols
import ar.team.stockify.domain.User

class SetUserUseCase(private val stockifyRepository: StockifyRepository) {
    suspend fun invoke(name: String, avatar: String): User = stockifyRepository.setUser(name, avatar)
}
