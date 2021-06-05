package ar.team.stockify.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StockDao {

    @Query("SELECT * FROM Stock")
    suspend fun getAllFav(): List<Stock>

    @Insert
    suspend fun insert (stock: Stock)

    @Delete
    suspend fun delete (stock: Stock)

}