package ar.team.stockify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import ar.team.stockify.network.AlphaVantage
import ar.team.stockify.network.Keys
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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

    }
}