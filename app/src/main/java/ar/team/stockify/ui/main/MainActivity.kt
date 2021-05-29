package ar.team.stockify.ui.main

import android.database.sqlite.SQLiteException
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ar.team.stockify.R
import ar.team.stockify.database.LocalStock
import ar.team.stockify.database.StockDatabase
import ar.team.stockify.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var db: StockDatabase

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomMenu.setupWithNavController(navController)

       /* TODO descomentar para cuando este preparado el DetailFragment
       navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.detailFragment) {
                binding.bottomMenu.visibility = View.GONE
            } else {
                binding.bottomMenu.visibility = View.VISIBLE
            }
        }
        */

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