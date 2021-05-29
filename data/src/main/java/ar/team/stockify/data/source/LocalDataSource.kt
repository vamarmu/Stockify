package ar.team.stockify.data.source

import ar.team.stockify.domain.User

interface LocalDataSource {
    fun getUser(): User
    fun hasUser(): Boolean
    fun setUser(name: String, avatar: String): User
}