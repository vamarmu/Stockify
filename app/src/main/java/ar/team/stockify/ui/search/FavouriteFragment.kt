package ar.team.stockify.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.team.stockify.databinding.FragmentFavouriteBinding
import ar.team.stockify.ui.details.DetailFragment
import ar.team.stockify.ui.details.toBestMatchesDataView
import ar.team.stockify.ui.details.toStockDataView
import ar.team.stockify.ui.main.MainContentFragmentDirections
import ar.team.stockify.ui.model.BestMatchesDataView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private val favouritesViewModel: FragmentViewModel  by viewModels()
    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }




    private fun loadFavourites(){
        val recyclerViewFavourites = binding.recyclerviewFavourites
        view?.let {
            val managerFavourites = LinearLayoutManager(it.context.applicationContext)
            recyclerViewFavourites.layoutManager = managerFavourites
        }
        favouritesViewModel.favouritesAdapter = FavouritesAdapter(FavouriteClickListener { stock ->
            findNavController().navigate(MainContentFragmentDirections.actionToDetail(stock.toStockDataView()))

        })
        favouritesViewModel.findFavourites()
        recyclerViewFavourites.adapter = favouritesViewModel.favouritesAdapter
    }

    override fun onResume() {
        super.onResume()

        loadFavourites()
    }

}