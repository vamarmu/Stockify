package ar.team.stockify.ui.main

import android.database.sqlite.SQLiteException
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.team.stockify.R
import ar.team.stockify.StockifyApp
import ar.team.stockify.database.LocalStock
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = applicationContext as StockifyApp
        setContentView(R.layout.activity_main)

        /*//Ejemplos de llamados de Red
        lifecycleScope.launch {
        val result = AlphaVantage.service.getCompanyOverview("EARNINGS", "IBM", Keys.apiKey())
        Timber.d("${javaClass.simpleName} -> Network call to Get Company Overview Endpoint")
        println(result.symbol)
        }


        lifecycleScope.launch {
            val result = AlphaVantage.service.getSymbolSearch("SYMBOL_SEARCH", "tesco", Keys.apiKey())
            Timber.d("${javaClass.simpleName} -> Network call to Get Symbol Search Endpoint")
            println(result.bestMatches[0].symbol)
        }*/

        //Ejemplos de Base de Datos

       // val stock1 = LocalStock(symbol = "TSLA", name = "Tesla")
       // val stock2 = LocalStock(symbol = "AAPL", name = "Apple")


        /*/lifecycleScope.launch {
            try {
                app.room.stockDao().insert(stock1)
                app.room.stockDao().insert(stock2)
            } catch (e: SQLiteException) {
                println(e.message)
            }

        }

        lifecycleScope.launch {
            val favStocks = app.room.stockDao().getAllFav()

            favStocks.forEach{
                println("STOCK SAVED"+ it.symbol + "" + it.name)
            }
        }*/
    }
}