package ar.team.stockify.usecases

import ar.team.stockify.data.repository.UserRepository
import ar.team.stockify.domain.User

class SetUserUseCase(private val userRepository: UserRepository) {
    fun invoke(name: String, avatar: String): User = userRepository.setUser(name, avatar)
}
