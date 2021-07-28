package ar.team.stockify.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StockDao {

    @Query("SELECT * FROM LocalStock")
    suspend fun getAllFav(): List<LocalStock>

    @Insert
    suspend fun insert (stock: LocalStock)

    @Delete
    suspend fun delete (stock: LocalStock)

}