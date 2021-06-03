package ar.team.stockify.database

import ar.team.stockify.data.source.LocalDataSource
import ar.team.stockify.domain.Stock
import ar.team.stockify.ui.details.toLocalStock
import ar.team.stockify.ui.details.toStock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LocalDataSourceImp (stockDatabase: StockDatabase):LocalDataSource {
    private val stockDAO=stockDatabase.stockDao()
    override suspend fun getSymbols(): List<Stock> = withContext(Dispatchers.IO) {
        stockDAO.getAllFav().map {it.toStock()}.toList()
    }

    override suspend fun addRemoveFavourite(stock: Stock) {
        val favStock: LocalStock? = stockDAO.getAllFav().find { it.toStock() == stock }
        if (favStock?.toStock() == stock) {
            stockDAO.delete(favStock)
        } else {
            stockDAO.insert(stock.toLocalStock())
        }

    }

}
