package ar.team.stockify.data.source

import ar.team.stockify.domain.User

import ar.team.stockify.domain.Stock
interface LocalDataSource {
    fun getUser(): User
    fun hasUser(): Boolean
    fun setUser(name: String, avatar: String): User

    suspend fun getSymbols(): List<Stock>
}