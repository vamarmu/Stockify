package ar.team.stockify.usecases

import ar.team.stockify.data.repository.UserRepository
import ar.team.stockify.domain.User

class GetUserUseCase(private val userRepository: UserRepository) {
    fun invoke(): User = userRepository.getUser()
}
