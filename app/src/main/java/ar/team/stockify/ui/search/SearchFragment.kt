package ar.team.stockify.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import ar.team.stockify.databinding.FragmentFavouritesBinding
import ar.team.stockify.ui.details.DetailsActivity
import ar.team.stockify.ui.details.toBestMatchesDataView
import ar.team.stockify.ui.model.BestMatchesDataView

class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

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
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(this)
        searchViewModel = getViewModel{
            SearchViewModel()
        }
        val recyclerView = binding.recyclerview
        val recyclerViewFavourites = binding.recyclerviewFavourites

        val manager = LinearLayoutManager(view.context.applicationContext)
        val managerFavourites = LinearLayoutManager(view.context.applicationContext)

        searchViewModel.adapter = SearchAdapter(SearchClickListener { bestMatches ->
            startDetailsActivity(bestMatches.toBestMatchesDataView())
        })
       
        recyclerViewFavourites.layoutManager = managerFavourites
        recyclerView.setLayoutManager(manager)

        recyclerViewFavourites.adapter = searchViewModel.adapter
        recyclerView.adapter = searchViewModel.adapter
    }

    private fun startDetailsActivity(bestMatches: BestMatchesDataView) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.DATA, bestMatches)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun onQueryTextSubmit(filter: String): Boolean {
        searchViewModel.onQueryTextSubmit(filter)
        return false
    }

    override fun onQueryTextChange(filter: String): Boolean {
        searchViewModel.onQueryTextChange(filter)
        return false
    }


}
@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this, vmFactory).get()
}
