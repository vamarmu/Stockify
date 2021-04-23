package ar.team.stockify.search

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import ar.team.stockify.model.BestMatches
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application), SearchImpl {

    private val _items = MutableLiveData<List<Symbol>>()
    val items: LiveData<List<Symbol>>
        get() = _items
    val adapter = SearchAdapter(SearchClickListener {
        //TODO(Redirigir a la pantalla de detalle)
        Toast.makeText(application, it, Toast.LENGTH_LONG).show()
    })
    init {
        adapter.addListWithHeader(mutableListOf(BestMatches(
            "a",
            "b",
            "c",
            "b",
            "c",
            "b",
            "c",
            "b",
            "c"
        )))
    }



    private var filter_actual = ""

    override fun onQueryTextSubmit(filter: String) {
        viewModelScope.launch {
            adapter.onQueryTextSubmit(filter)
        }
    }

    override fun onQueryTextChange(filter: String) {
        viewModelScope.launch {
            if (filter.length == 1 && filter_actual != filter) {
                filter_actual = filter
                //  Llamada al api
                adapter.onQueryTextChange(filter)

            } else {
                adapter.onQueryTextChange(filter)
            }
        }
    }

}