package ar.team.stockify.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import ar.team.stockify.R
import ar.team.stockify.databinding.ActivityDetailsBinding
import ar.team.stockify.search.SearchAdapter
import ar.team.stockify.search.SearchClickListener
import ar.team.stockify.search.SearchViewModel
import ar.team.stockify.search.SearchViewModelFactory

class DetailsActivity : AppCompatActivity() {
    private lateinit var detailsViewModel: DetailsViewModel
    companion object{
        const val DATA = "DetailActivity:detail"
    }

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
        //detailsViewModel = detailsViewModelFactory().create(DetailsViewModel::class.java)

        val recyclerView = binding.detailsRecycler

       // val manager = LinearLayoutManager(view.context.applicationContext)

        //val manager = LinearLayoutManager(view.context.applicationContext)
        //recyclerView.setLayoutManager(manager)
        //recyclerView.adapter = detailsViewModel.adapter

    }
}