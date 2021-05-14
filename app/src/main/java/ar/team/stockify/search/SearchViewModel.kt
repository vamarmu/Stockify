package ar.team.stockify.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.team.stockify.model.BestMatches
import ar.team.stockify.network.AlphaVantage
import ar.team.stockify.network.Keys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SearchViewModel() : ViewModel(), SearchImpl, AddSymbols {

    private var _items = listOf<BestMatches>()


    lateinit var adapter: SearchAdapter


    init {
        //TODO Carga de favoritos
    }

    private var filter_actual = ""
    override fun onQueryTextSubmit(filter: String) {
        viewModelScope.launch {
            if (filter.length != 1 && filter != filter_actual) {
                val result =
                    AlphaVantage.service.getSymbolSearch("SYMBOL_SEARCH", filter, Keys.apiKey())
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
                    val result = AlphaVantage.service.getSymbolSearch(
                        "SYMBOL_SEARCH",
                        filter,
                        ar.team.stockify.network.Keys.apiKey()
                    )
                    Timber.d("${javaClass.simpleName} -> Network call to Get Symbol Search Endpoint")
                    _items = result.bestMatches
                    addListWithoutHeader(_items)
                }
            }
        }
    }

    override fun addListWithoutHeader(list: List<BestMatches>?) {
        adapter.addListWithoutHeader(list)
    }

    override fun addListWithHeader(list: List<BestMatches>?) {
        // TODO Añadir elementos a la lista de favoritos
    }

}