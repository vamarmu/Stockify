package ar.team.stockify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import ar.team.stockify.database.Stock
import ar.team.stockify.network.AlphaVantage
import ar.team.stockify.network.Keys
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    val app = applicationContext as StockifyApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Ejemplos de llamados de Red
        lifecycleScope.launch {
        val result = AlphaVantage.service.getCompanyOverview("EARNINGS", "IBM", Keys.apiKey())
        Timber.d("${javaClass.simpleName} -> Network call to Get Company Overview Endpoint")
        println(result.symbol)
        }


        lifecycleScope.launch {
            val result = AlphaVantage.service.getSymbolSearch("SYMBOL_SEARCH", "tesco", Keys.apiKey())
            Timber.d("${javaClass.simpleName} -> Network call to Get Symbol Search Endpoint")
            println(result.bestMatches[0].symbol)
        }

        //Ejemplos de Base de Datos

        val stock1 = Stock(symbol = "TSLA", name = "Tesla")
        val stock2 = Stock (symbol = "AAPL", name = "Apple")


        lifecycleScope.launch {
            app.room.stockDao().insert(stock1)
        }

        lifecycleScope.launch {
            val favStocks = app.room.stockDao().getAllFav()

            favStocks.forEach{
                println(it.symbol + "" + it.name)
            }
        }
    }
}