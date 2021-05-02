package ar.team.stockify.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ar.team.stockify.R

class DetailsActivity : AppCompatActivity() {
    companion object{
        const val DATA = "DetailActivity:detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val detailsData = intent.getSerializableExtra(DATA).toString()
        Log.d("que es esto???", detailsData)
    }
}