package ar.team.stockify.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Stock::class], version = 1)
abstract class StockDatabase : RoomDatabase() {

    abstract fun stockDao(): StockDao

}