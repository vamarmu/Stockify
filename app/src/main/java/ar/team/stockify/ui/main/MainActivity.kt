package ar.team.stockify.ui.main

import android.database.sqlite.SQLiteException
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.team.stockify.R
import ar.team.stockify.database.LocalStock
import ar.team.stockify.database.StockDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var db: StockDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stock1 = LocalStock(symbol = "TSLA", name = "Tesla")
        val stock2 = LocalStock(symbol = "AAPL", name = "Apple")


        lifecycleScope.launch {
            try {
                db.stockDao().insert(stock1)
                db.stockDao().insert(stock2)
            } catch (e: SQLiteException) {
                println(e.message)
            }

        }

        lifecycleScope.launch {
            val favStocks = db.stockDao().getAllFav()

            favStocks.forEach {
                println("STOCK SAVED" + it.symbol + "" + it.name)
            }
        }
    }
}