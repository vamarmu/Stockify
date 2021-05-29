package ar.team.stockify.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.team.stockify.domain.BestMatches
import ar.team.stockify.usecases.GetStocksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getStocksUseCase: GetStocksUseCase
) : ViewModel(){

    private var _items = listOf<BestMatches>()

    lateinit var adapter: SearchAdapter

    private var searchJob: Job? = null

    private var filterActual = ""

     fun onQueryTextSubmit(filter: String) {
        if (filter.length != 1 && filter != filterActual) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                val result = getStocksUseCase.invoke(filter)
                Timber.d("${javaClass.simpleName} -> Network call to Get Symbol Search Endpoint")
                _items = result.bestMatches
                adapter.addListWithoutHeader(_items)
                filterActual=filter
            }
        }
    }

     fun onQueryTextChange(filter: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            withContext(Dispatchers.Main) {
                if (_items.isEmpty() || _items.size > 5 && filterActual!=filter) {
                    val result = getStocksUseCase.invoke(filter)
                    Timber.d("${javaClass.simpleName} -> Network call to Get Symbol Search Endpoint")
                    _items = result.bestMatches
                    addListWithoutHeader(_items)
                    filterActual=filter
                }
            }
        }
    }

    private fun addListWithoutHeader(list: List<BestMatches>?) {
        adapter.addListWithoutHeader(list)
    }


}