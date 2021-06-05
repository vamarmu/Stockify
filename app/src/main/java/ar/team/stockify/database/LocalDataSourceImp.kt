package ar.team.stockify.database

import android.content.SharedPreferences
import androidx.core.content.edit
import ar.team.stockify.data.source.LocalDataSource
import ar.team.stockify.domain.Stock
import ar.team.stockify.ui.details.toLocalStock
import ar.team.stockify.domain.User
import ar.team.stockify.storage.PreferenceHelper.get
import ar.team.stockify.ui.details.toStock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



class LocalDataSourceImp (private val stockDatabase: StockDatabase, private val preferences: SharedPreferences): LocalDataSource {

    private val stockDAO=stockDatabase.stockDao()



    override suspend fun getSymbols(): List<Stock> = withContext(Dispatchers.IO) {
        stockDAO.getAllFav().map {it.toStock()}.toList()
    }

    override suspend fun getUser(): User? =
        if (preferences.contains(PREFERENCE_NAME_USER)
            && preferences.contains(PREFERENCE_NAME_USER) ) {
           User(preferences[PREFERENCE_NAME_USER],preferences[PREFERENCE_IMAGE_USER])
        } else null

    override suspend fun setUser(name: String, image: String): User {
        preferences.edit {
            putString(PREFERENCE_NAME_USER,name)
            putString(PREFERENCE_IMAGE_USER,image)
            commit()
    }
        return User (name,image)
    }

    override suspend fun addRemoveFavourite(stock: Stock) {
        val favStock: LocalStock? = stockDAO.getAllFav().find { it.toStock() == stock }
        if (favStock?.toStock() == stock) {
            stockDAO.delete(favStock)
        } else {
    override suspend fun removeUser() {
        preferences.edit {
            clear()
            commit()
        }
            stockDAO.insert(stock.toLocalStock())
        }

    companion object {
        const val PREFERENCE_NAME_USER = "nameUer"
        const val PREFERENCE_IMAGE_USER = "imageUser"
    }

}

