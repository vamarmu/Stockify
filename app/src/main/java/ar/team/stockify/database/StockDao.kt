package ar.team.stockify.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StockDao {

    @Query("SELECT * FROM Stock")
    suspend fun getAllFav(): List<Stock>

    @Query("SELECT EXISTS (SELECT 1 FROM Stock WHERE symbol = :symbol)")
    suspend fun exists(symbol: String): Boolean

    @Insert
    suspend fun insert (stock: Stock)

    @Delete
    suspend fun delete (stock: Stock)

}