package ar.team.stockify.ui.details

import androidx.lifecycle.*

import ar.team.stockify.network.AlphaVantage
import ar.team.stockify.network.Keys
import ar.team.stockify.network.model.RemoteCompany
import ar.team.stockify.network.model.RemoteQuarterlyEarning
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel() : ViewModel() {


    private val _detailsQuarter = MutableLiveData<List<RemoteQuarterlyEarning>>()
    val detailsQuarter: LiveData<List<RemoteQuarterlyEarning>>
        get() = _detailsQuarter

    private val _company = MutableLiveData<RemoteCompany>()
    val company: LiveData<RemoteCompany>
        get() = _company

    fun onQueryCompanyDetails(filter: String) {

        viewModelScope.launch {
            val companyResponse = AlphaVantage.service.getCompanyOverview(
                filter,
                Keys.apiKey())
            Timber.d("${javaClass.simpleName} -> Network call to Get Company Overview Endpoint")
            _company.value = companyResponse
            _detailsQuarter.value = companyResponse?.quarterlyEarnings

            println(companyResponse.quarterlyEarnings)
            }
        }
    }




