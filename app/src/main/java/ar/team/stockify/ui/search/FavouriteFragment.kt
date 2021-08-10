package ar.team.stockify.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.team.stockify.databinding.FragmentFavouriteBinding
import ar.team.stockify.ui.details.toStockDataView
import ar.team.stockify.ui.main.MainContentFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private val favouriteViewModel: FavouriteViewModel  by viewModels()
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
        favouriteViewModel.favouritesAdapter = FavouritesAdapter(FavouriteClickListener { stock ->
            findNavController().navigate(MainContentFragmentDirections.actionToDetail(stock.toStockDataView()))

        })
        favouriteViewModel.findFavourites()
        recyclerViewFavourites.adapter = favouriteViewModel.favouritesAdapter
    }

    override fun onResume() {
        super.onResume()

        loadFavourites()
    }

}