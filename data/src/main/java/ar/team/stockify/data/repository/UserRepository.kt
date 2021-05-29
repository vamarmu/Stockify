package ar.team.stockify.data.repository

import ar.team.stockify.data.source.LocalDataSource
import ar.team.stockify.data.source.RemoteDataSource
import ar.team.stockify.domain.Symbols
import ar.team.stockify.domain.User

class UserRepository(
    private val localDataSource: LocalDataSource
) {
    fun getUser(): User = localDataSource.getUser()
    fun hasUser(): Boolean = localDataSource.hasUser()
    fun setUser(name: String, avatar: String): User = localDataSource.setUser(name, avatar)
}