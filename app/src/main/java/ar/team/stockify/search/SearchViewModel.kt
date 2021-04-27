package ar.team.stockify.search

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import ar.team.stockify.model.BestMatches
import kotlinx.coroutines.launch

class SearchViewModel() : ViewModel(), SearchImpl, AddSymbols {

    private val _items = MutableLiveData<List<SealedSymbol>>()
    val items: LiveData<List<SealedSymbol>>
        get() = _items


    val adapter = SearchAdapter(SearchClickListener {
        //TODO(Redirigir a la pantalla de detalle)

    })

    val favorite_adapter = SearchAdapter(SearchClickListener {
        //TODO(Redirigir a la pantalla de detalle)
    })


    init {
        //TODO Carga de favoritos
    }

    private var filter_actual = ""

    override fun onQueryTextSubmit(filter: String) {
        viewModelScope.launch {
            adapter.onQueryTextSubmit(filter)
            favorite_adapter.onQueryTextSubmit(filter)
        }
    }

    override fun onQueryTextChange(filter: String) {
        viewModelScope.launch {
            if (filter.length == 1 && filter_actual != filter) {
                filter_actual = filter
                // TODO LLamada al API
                adapter.onQueryTextChange(filter)
            } else {
                adapter.onQueryTextChange(filter)
            }
        }
    }

    override fun addListWithoutHeader(list: List<BestMatches>?) {
        adapter.addListWithoutHeader(list)
    }

    override fun addListWithHeader(list: List<BestMatches>?) {
        favorite_adapter.addListWithHeader(list)
    }

}