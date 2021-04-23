package ar.team.stockify.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.team.stockify.R
import ar.team.stockify.model.SearchItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavouritesFragment : Fragment(), SearchImpl {

    private val searchViewModel = SearchViewModel()

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

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val recyclerView_favourites = view.findViewById<RecyclerView>(R.id.recyclerview_favourites)
        val manager = LinearLayoutManager(view.context.applicationContext)
        val manager_favourites = LinearLayoutManager(view.context.applicationContext)

        recyclerView_favourites.layoutManager = manager_favourites
        recyclerView.setLayoutManager(manager)

        recyclerView_favourites.adapter = searchViewModel.adapter.value
        recyclerView.adapter = searchViewModel.adapter.value
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