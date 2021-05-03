package ar.team.stockify.details

import androidx.lifecycle.*
import ar.team.stockify.databinding.DetailsListItemBinding
import ar.team.stockify.model.QuarterlyEarning
import ar.team.stockify.network.AlphaVantage
import ar.team.stockify.network.Keys
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel() : ViewModel() {


    private val _detailsQuarter = MutableLiveData<List<QuarterlyEarning>>()
    val detailsQuarter: LiveData<List<QuarterlyEarning>>
        get() = _detailsQuarter

    private val _symbol = MutableLiveData<String>()
    val symbol: LiveData<String> get() = _symbol

    private val _estimatedEPS = MutableLiveData<String>()
    val estimatedEPS: LiveData<String> get() = _estimatedEPS

    private val _fiscalDateEnding= MutableLiveData<String>()
    val fiscalDateEnding: LiveData<String> get() = _fiscalDateEnding

    private val _reportedEPS= MutableLiveData<String>()
    val reportedEPS: LiveData<String> get() = _reportedEPS

    private val _surprise = MutableLiveData<String>()
    val surprise: LiveData<String> get() = _surprise




    init {
          initializeView()
    }


    fun onQueryCompanyDetails(filter: String) {

        viewModelScope.launch {
            val result = AlphaVantage.service.getCompanyOverview(
                "EARNINGS",
                filter,
                Keys.apiKey())
            Timber.d("${javaClass.simpleName} -> Network call to Get Company Overview Endpoint")
            println(result.symbol)
            _detailsQuarter.value = result.quarterlyEarnings
            }
        }


    private fun initializeView()  {
        _detailsQuarter.value?.run {
            _estimatedEPS.value = estimatedEPS.toString()
            _fiscalDateEnding.value =  fiscalDateEnding.toString()
            _surprise.value = surprise.toString()
        }
    }

}



