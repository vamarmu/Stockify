package ar.team.stockify.details

import androidx.lifecycle.*
import ar.team.stockify.model.BestMatches
import ar.team.stockify.network.AlphaVantage
import ar.team.stockify.network.Keys
import ar.team.stockify.search.AddSymbols
import ar.team.stockify.search.SearchAdapter
import ar.team.stockify.search.SearchImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class DetailsViewModel() : ViewModel() {


    private val _items = MutableLiveData<List<BestMatches>>()
    val items: LiveData<List<BestMatches>>
        get() = _items


    lateinit var adapter: DetailsAdapter


    private var filter_actual = ""
    override fun onQueryCompanyDetails(filter: String) {

        viewModelScope.launch {
            val result = AlphaVantage.service.getCompanyOverview(
                "EARNINGS",
                "IBM",
                Keys.apiKey())
            Timber.d("${javaClass.simpleName} -> Network call to Get Company Overview Endpoint")
            println(result.symbol)
            //_items.value = result.symbol
            addDetailsList(items.value)
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