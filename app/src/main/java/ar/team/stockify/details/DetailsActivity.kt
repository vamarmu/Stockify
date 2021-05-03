package ar.team.stockify.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ar.team.stockify.R
import ar.team.stockify.databinding.ActivityDetailsBinding
import ar.team.stockify.search.SearchAdapter
import ar.team.stockify.search.SearchClickListener
import ar.team.stockify.search.SearchViewModel
import ar.team.stockify.search.SearchViewModelFactory
import androidx.lifecycle.Observer
import ar.team.stockify.model.Company
import ar.team.stockify.model.QuarterlyEarning

class DetailsActivity : AppCompatActivity() {
    companion object{
        const val DATA = "DetailActivity:detail"
    }

    //llamamos al viewModelProvider durante nuestro ciclo de vida
    private val detailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailsData = intent.getSerializableExtra(DATA).toString()
        Log.d("que es esto???", detailsData)

        /** TODO Una vez que tienes el symbol deberias de pasarlo al ViewModel y que el se encargue de
        realizar la petición al servidor . Puedes guiarte como lo tiene en SearchViewModel.
        Si quieres usar databiding debes añadirlo al build.gradle y  layout. Revisa este enlace
        https://developer.android.com/topic/libraries/data-binding/start
        Tambien tiene un ejemplo Antonio en el curso.
         */

        detailsViewModel.onQueryCompanyDetails(detailsData)

    }
}