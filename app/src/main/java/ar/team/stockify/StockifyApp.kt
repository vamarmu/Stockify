package ar.team.stockify

import android.app.Application
import androidx.room.Room
import ar.team.stockify.database.StockDatabase
import timber.log.Timber

class StockifyApp: Application() {

    val room = Room
        .databaseBuilder(this,StockDatabase::class.java, "stock-db")
        .build()

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}