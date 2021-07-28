package ar.team.stockify.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ar.team.stockify.databinding.FragmentFavouriteBinding
import ar.team.stockify.ui.details.DetailFragment
import ar.team.stockify.ui.details.toBestMatchesDataView
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewFavourites = binding.recyclerviewFavourites
        val managerFavourites = LinearLayoutManager(view.context.applicationContext)

        favouritesViewModel.favouritesAdapter = FavouritesAdapter(FavouriteClickListener { stock ->
            startDetailsActivity( stock.toBestMatchesDataView())
        })

        recyclerViewFavourites.layoutManager = managerFavourites

        recyclerViewFavourites.adapter = favouritesViewModel.favouritesAdapter



    }

    private fun startDetailsActivity(bestMatches: BestMatchesDataView) {
        val intent = Intent(context, DetailFragment::class.java)
        intent.putExtra(DetailFragment.DATA, bestMatches)
        startActivity(intent)
    }
}