package ar.team.stockify.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.team.stockify.data.repository.StocksRepository
import ar.team.stockify.domain.BestMatches
import ar.team.stockify.network.Keys
import ar.team.stockify.network.RemoteDataSourceImp
import ar.team.stockify.usecases.GetStocksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getStocksUseCase: GetStocksUseCase
) : ViewModel(), SearchImpl, AddSymbols {

    private var _items = listOf<BestMatches>()

    lateinit var adapter: SearchAdapter

    /*private val getStocksUseCase: GetStocksUseCase = GetStocksUseCase(
        StocksRepository(
            apiKey = Keys.apiKey(),
            remoteDataSource = RemoteDataSourceImp()
        )
    )*/

    init {
        //TODO Carga de favoritos
    }

    private var filter_actual = ""
    override fun onQueryTextSubmit(filter: String) {
        viewModelScope.launch {
            if (filter.length != 1 && filter != filter_actual) {
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

    override fun addListWithoutHeader(list: List<BestMatches>?) {
        adapter.addListWithoutHeader(list)
    }

    override fun addListWithHeader(list: List<BestMatches>?) {
        // TODO Añadir elementos a la lista de favoritos
    }

}