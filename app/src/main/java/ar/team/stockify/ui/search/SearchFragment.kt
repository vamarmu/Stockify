package ar.team.stockify.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.team.stockify.databinding.FragmentSearchBinding
import ar.team.stockify.ui.details.DetailFragment
import ar.team.stockify.ui.details.toBestMatchesDataView
import ar.team.stockify.ui.details.toStockDataView
import ar.team.stockify.ui.main.MainContentFragmentDirections
import ar.team.stockify.ui.model.BestMatchesDataView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(){

        private val searchViewModel: SearchViewModel by viewModels()
        private lateinit var binding: FragmentSearchBinding

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentSearchBinding.inflate(inflater, container, false)
            return binding.root

        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val searchView = binding.searchView

            val recyclerView = binding.recyclerview

            val manager = LinearLayoutManager(view.context.applicationContext)

            searchViewModel.adapter = SearchAdapter(SearchClickListener { stock ->
                findNavController().navigate(MainContentFragmentDirections.actionToDetail(stock.toStockDataView()))
            })

            recyclerView.layoutManager = manager
            recyclerView.adapter = searchViewModel.adapter

            val setTextChange: (String?) -> Unit = { newText ->
                newText?.let {
                    searchViewModel.onQueryTextChange(newText)
                }
            }
            val setTextSubmit: (String?) -> Unit = { newText ->
                newText?.let {
                    searchViewModel.onQueryTextChange(newText)
                }
            }
            searchView.setOnQueryTextListener(
                QueryTextListener(this.lifecycle, setTextChange, setTextSubmit)
            )
        }


        internal class QueryTextListener(
            lifecycle: Lifecycle,
            private val queryTextChange: (String?) -> Unit,
            private val queryTextSubmit: (String?) -> Unit
        ) : SearchView.OnQueryTextListener {

            private val coroutineScope = lifecycle.coroutineScope

            private var searchJob: Job? = null

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    query?.let {
                        queryTextSubmit(query)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    newText?.let {
                        queryTextChange(newText)
                    }
                }
                return false
            }
        }

    }


    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {

        val vmFactory = object : ViewModelProvider.Factory {
            override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
        }

        return ViewModelProvider(this, vmFactory).get()
    }

