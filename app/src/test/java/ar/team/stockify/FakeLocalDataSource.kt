package ar.team.stockify

import ar.team.stockify.data.source.LocalDataSource
import ar.team.stockify.database.LocalStock
import ar.team.stockify.domain.Stock
import ar.team.stockify.domain.User
import ar.team.stockify.ui.details.toLocalStock
import ar.team.stockify.ui.details.toStock


class FakeLocalDataSource: LocalDataSource {

    private var user: User? = null

    private var favourites: MutableList<LocalStock> = mutableListOf()

    override suspend fun getSymbols(): List<Stock> = emptyList()

    override suspend fun getUser(): User? = user

    override suspend fun setUser(name: String, image: String): User {
        user = User(name, image)
        return user!!
    }

    override suspend fun removeUser() {
        user = null
    }

    override suspend fun addRemoveFavourite(stock: Stock): Boolean {
        val favStock: LocalStock? = favourites.find { it.toStock() == stock }
        return if (favStock?.toStock() == stock) {
            favourites.remove(favStock)
            false
        } else {
            favourites.add(stock.toLocalStock())
            true
        }
    }
}


