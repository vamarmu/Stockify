package ar.team.stockify.usecases

import ar.team.stockify.data.repository.UserRepository

class HasUserUseCase(private val userRepository: UserRepository) {
    fun invoke(): Boolean = userRepository.hasUser()
}
