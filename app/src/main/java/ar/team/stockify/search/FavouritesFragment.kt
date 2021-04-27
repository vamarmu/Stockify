package ar.team.stockify.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.team.stockify.R


class FavouritesFragment : Fragment(), SearchImpl {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel =
            SearchViewModelFactory().create(SearchViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val recyclerView_favourites = view.findViewById<RecyclerView>(R.id.recyclerview_favourites)

        val manager = LinearLayoutManager(view.context.applicationContext)
        val manager_favourites = LinearLayoutManager(view.context.applicationContext)

        recyclerView_favourites.layoutManager = manager_favourites
        recyclerView.setLayoutManager(manager)

        recyclerView_favourites.adapter = searchViewModel.favorite_adapter
       recyclerView.adapter = searchViewModel.adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouritesFragment()
    }

    override fun onQueryTextSubmit(filter: String) {
        searchViewModel.onQueryTextSubmit(filter)
    }

    override fun onQueryTextChange(filter: String) {
        searchViewModel.onQueryTextChange(filter)
    }


}