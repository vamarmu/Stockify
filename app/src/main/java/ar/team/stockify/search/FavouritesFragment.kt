package ar.team.stockify.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.team.stockify.R
import ar.team.stockify.model.BestMatches


class FavouritesFragment : Fragment(), SearchImpl {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel =
            SearchViewModelFactory(requireNotNull(activity).application).create(SearchViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val recyclerView_favourites = view.findViewById<RecyclerView>(R.id.recyclerview_favourites)
        val manager = LinearLayoutManager(view.context.applicationContext)
        val manager_favourites = LinearLayoutManager(view.context.applicationContext)

        recyclerView_favourites.layoutManager = manager_favourites
        recyclerView.setLayoutManager(manager)

        recyclerView_favourites.adapter = searchViewModel?.favorite_adapter
        searchViewModel?.adapter.submitList(searchViewModel?.items.value)
        recyclerView.adapter = searchViewModel?.adapter
        searchViewModel.addListWithHeader(
            mutableListOf(
                BestMatches(
                    "a",
                    "b",
                    "c",
                    "b",
                    "c",
                    "b",
                    "c",
                    "b",
                    "c"
                ), BestMatches(
                    "C",
                    "a",
                    "c",
                    "b",
                    "c",
                    "b",
                    "c",
                    "b",
                    "c"
                )
            )
        )
        searchViewModel.addListWithoutHeader(mutableListOf(BestMatches(
            "a",
            "b",
            "c",
            "b",
            "c",
            "b",
            "c",
            "b",
            "c")))
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouritesFragment()
    }

    override fun onQueryTextSubmit(filter: String) {
        searchViewModel?.adapter?.onQueryTextSubmit(filter)
    }

    override fun onQueryTextChange(filter: String) {
        searchViewModel?.adapter?.onQueryTextChange(filter)
    }


}