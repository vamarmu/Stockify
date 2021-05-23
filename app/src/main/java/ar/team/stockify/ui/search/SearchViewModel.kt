package ar.team.stockify.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.team.stockify.domain.BestMatches
import ar.team.stockify.domain.Stock
import ar.team.stockify.usecases.GetFavouritesUseCase
import ar.team.stockify.usecases.GetStocksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SearchViewModel(
    private var getStocksUseCase: GetStocksUseCase
) : ViewModel(), SearchImpl {

    private var _items = listOf<BestMatches>()

    lateinit var adapter: SearchAdapter

    private var searchJob: Job? = null

    private var filterActual = ""

    override fun onQueryTextSubmit(filter: String) {
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

    override fun onQueryTextChange(filter: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            withContext(Dispatchers.Main) {
                if (_items.isEmpty() || _items.size > 5 && filterActual==filter) {
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