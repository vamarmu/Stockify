package ar.team.stockify.ui.details

import androidx.lifecycle.*
import ar.team.stockify.domain.Stock

import ar.team.stockify.model.Company
import ar.team.stockify.model.QuarterlyEarning
import ar.team.stockify.network.AlphaVantage
import ar.team.stockify.network.Keys
import ar.team.stockify.network.model.RemoteQuarterlyEarning
import ar.team.stockify.usecases.AddRemoveFavUseCase
import ar.team.stockify.usecases.GetFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val addRemoveFavUseCase: AddRemoveFavUseCase
) : ViewModel() {


    private val _detailsQuarter = MutableLiveData<List<RemoteQuarterlyEarning>>()
    val detailsQuarter: LiveData<List<RemoteQuarterlyEarning>>
        get() = _detailsQuarter


    private val _favStock = MutableLiveData<Boolean>()
    val favStock: LiveData<Boolean> = _favStock

    fun onQueryCompanyDetails(filter: String) {

        viewModelScope.launch {
            val companyResponse = AlphaVantage.service.getCompanyOverview(
                filter,
                Keys.apiKey()
            )
            Timber.d("${javaClass.simpleName} -> Network call to Get Company Overview Endpoint")
            if (!companyResponse.remoteQuarterlyEarnings.isNullOrEmpty()) {
                _detailsQuarter.value = companyResponse.remoteQuarterlyEarnings
            }

        }
    }

    fun addRemoveFavourites(stock: Stock) {
        viewModelScope.launch {
            addRemoveFavUseCase.invoke(stock).also {
                stockSaved(stock)
            }
        }
    }

    fun stockSaved(stock: Stock) {
        viewModelScope.launch {
            getFavouritesUseCase.invoke().also {
                _favStock.postValue(it.contains(stock))
            }
        }
    }
}




