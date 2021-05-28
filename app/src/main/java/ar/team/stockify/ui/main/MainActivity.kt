package ar.team.stockify.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.team.stockify.R
import ar.team.stockify.database.StockDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var db: StockDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
