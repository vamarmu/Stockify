package ar.team.stockify.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ar.team.stockify.StockifyApp
import ar.team.stockify.data.repository.FavouritesRepository
import ar.team.stockify.database.RoomDataSourceImp
import ar.team.stockify.databinding.FragmentFavouriteBinding
import ar.team.stockify.ui.details.DetailsActivity
import ar.team.stockify.ui.details.toBestMatchesDataView
import ar.team.stockify.ui.model.BestMatchesDataView
import ar.team.stockify.usecases.GetFavouritesUseCase


class FavouriteFragment : Fragment() {

    private lateinit var favouritesViewModel: FragmentViewModel
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
        val app = this.context!!.applicationContext as StockifyApp

        favouritesViewModel = getViewModel {
            FragmentViewModel(
                GetFavouritesUseCase(
                    FavouritesRepository(
                        localDataSource = RoomDataSourceImp(app.room)
                    )
                )
            )
        }

        val recyclerViewFavourites = binding.recyclerviewFavourites


        val managerFavourites = LinearLayoutManager(view.context.applicationContext)

        favouritesViewModel.adapter = SearchAdapter(SearchClickListener { bestMatches ->
            startDetailsActivity(bestMatches.toBestMatchesDataView())
        })

        favouritesViewModel.favouritesadapter = FavouritesAdapter(FavouriteClickListener { stock ->
            startDetailsActivity( stock.toBestMatchesDataView())
        })

        recyclerViewFavourites.layoutManager = managerFavourites

        recyclerViewFavourites.adapter = favouritesViewModel.favouritesadapter



    }

    private fun startDetailsActivity(bestMatches: BestMatchesDataView) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.DATA, bestMatches)
        startActivity(intent)
    }
}