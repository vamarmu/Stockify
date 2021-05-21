package ar.team.stockify.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.team.stockify.domain.BestMatches
import ar.team.stockify.domain.Stock
import ar.team.stockify.usecases.GetFavouritesUseCase
import ar.team.stockify.usecases.GetStocksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SearchViewModel(
    private var getStocksUseCase: GetStocksUseCase,
    private var getFavouritesUseCase: GetFavouritesUseCase
) : ViewModel(), SearchImpl {

    private var _items = listOf<BestMatches>()

    lateinit var adapter: SearchAdapter
    lateinit var favouritesadapter: FavouritesAdapter

    init {
        viewModelScope.launch {
            addListWithHeader(getFavouritesUseCase.invoke())
        }

    }

    private var filterActual = ""
    override fun onQueryTextSubmit(filter: String) {
        viewModelScope.launch {
            if (filter.length != 1 && filter != filterActual) {
                val result = getStocksUseCase.invoke(filter)
                Timber.d("${javaClass.simpleName} -> Network call to Get Symbol Search Endpoint")
                _items = result.bestMatches
                adapter.addListWithoutHeader(_items)
            }
        }
    }

    override fun onQueryTextChange(filter: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                if (filter.length == 1 || _items.size > 5) {
                    val result = getStocksUseCase.invoke(filter)
                    Timber.d("${javaClass.simpleName} -> Network call to Get Symbol Search Endpoint")
                    _items = result.bestMatches
                    addListWithoutHeader(_items)
                }
            }
        }
    }

     private fun addListWithoutHeader(list: List<BestMatches>?) {
        adapter.addListWithoutHeader(list)
    }

     private fun addListWithHeader(list: List<Stock>?) {
        favouritesadapter.addListWithHeader(list)
    }

}