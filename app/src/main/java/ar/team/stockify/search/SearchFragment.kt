package ar.team.stockify.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import ar.team.stockify.databinding.FragmentFavouritesBinding
import ar.team.stockify.details.DetailsActivity
import ar.team.stockify.model.BestMatches

class SearchFragment : Fragment(), SearchImpl {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding : FragmentFavouritesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel =
            SearchViewModelFactory().create(SearchViewModel::class.java)

        val recyclerView = binding.recyclerview
        val recyclerViewFavourites = binding.recyclerviewFavourites

        val manager = LinearLayoutManager(view.context.applicationContext)
        val managerFavourites = LinearLayoutManager(view.context.applicationContext)

        searchViewModel.adapter = SearchAdapter(SearchClickListener {
                bestMatches ->
            startDetailsActivity(bestMatches)
        })

        recyclerViewFavourites.layoutManager = managerFavourites
        recyclerView.setLayoutManager(manager)

        recyclerViewFavourites.adapter = searchViewModel.adapter
        recyclerView.adapter = searchViewModel.adapter
    }

    private fun startDetailsActivity(bestMatches: BestMatches) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.DATA, bestMatches)

        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun onQueryTextSubmit(filter: String) {
        searchViewModel.onQueryTextSubmit(filter)
    }

    override fun onQueryTextChange(filter: String) {
        searchViewModel.onQueryTextChange(filter)
    }


}