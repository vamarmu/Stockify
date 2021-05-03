package ar.team.stockify.details

import androidx.lifecycle.*

import ar.team.stockify.model.Company
import ar.team.stockify.model.QuarterlyEarning
import ar.team.stockify.network.AlphaVantage
import ar.team.stockify.network.Keys
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel() : ViewModel() {


    private val _detailsQuarter = MutableLiveData<List<QuarterlyEarning>>()
    val detailsQuarter: LiveData<List<QuarterlyEarning>>
        get() = _detailsQuarter

    private val _company = MutableLiveData<Company>()
    val company: LiveData<Company>
        get() = _company

    fun onQueryCompanyDetails(filter: String) {

        viewModelScope.launch {
            val companyResponse = AlphaVantage.service.getCompanyOverview(
                "EARNINGS",
                filter,
                Keys.apiKey())
            Timber.d("${javaClass.simpleName} -> Network call to Get Company Overview Endpoint")
            _company.value = companyResponse
            _detailsQuarter.value = companyResponse?.quarterlyEarnings

            println(companyResponse.quarterlyEarnings)
            }
        }
    }




