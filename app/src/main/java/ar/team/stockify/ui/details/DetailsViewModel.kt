package ar.team.stockify.ui.details

import androidx.lifecycle.*
import ar.team.stockify.domain.Stock
import ar.team.stockify.domain.StockDetail
import ar.team.stockify.domain.User

import ar.team.stockify.network.AlphaVantage
import ar.team.stockify.network.Keys
import ar.team.stockify.network.model.RemoteQuarterlyEarning
import ar.team.stockify.ui.user.UserViewModel
import ar.team.stockify.usecases.AddRemoveFavUseCase
import ar.team.stockify.usecases.GetFavouritesUseCase
import ar.team.stockify.usecases.GetStockDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val addRemoveFavUseCase: AddRemoveFavUseCase,
    private val getStockDetailUseCase: GetStockDetailUseCase,
) : ViewModel() {

    sealed class UiDetailModel {
        object NoContent: UiDetailModel()
        class Content(val detail: List<StockDetail>): UiDetailModel()
        object Loading : UiDetailModel()
        class ToggleFavourite(val state : Boolean) : UiDetailModel()
    }

    private val _model = MutableLiveData<UiDetailModel>()
    val model: LiveData<UiDetailModel>
        get() = _model

    private val _detailsQuarter = MutableLiveData<List<StockDetail>>()
    val detailsQuarter: LiveData<List<StockDetail>>
        get() = _detailsQuarter


    private val _favStock = MutableLiveData<Boolean>()
    val favStock: LiveData<Boolean> = _favStock

    init {
        _model.value= UiDetailModel.Loading
    }


    fun onQueryCompanyDetails(stock: String) {

        viewModelScope.launch {
            val listDetail:List<StockDetail>? = getStockDetailUseCase.invoke(stock)
            Timber.d("${javaClass.simpleName} -> Network call to Get Company Overview Endpoint")
            println("TAG $listDetail")

            listDetail?.let { list ->
                if (listDetail.isNotEmpty()) {
                    _detailsQuarter.value = list
                    _model.value = UiDetailModel.Content(list)
                }
                else
                    _model.value = UiDetailModel.NoContent

            }
        }
    }

    fun addRemoveFavourites(stock: Stock) {
        viewModelScope.launch {
            addRemoveFavUseCase.invoke(stock).also {
                stockSaved(stock)
            }
            //TODO No sabemos si es o no favorito ¿?¿?¿?¿??
           /* favStock.value?.let { state ->
                UiDetailModel.ToggleFavourite(state)
            }*/
        }
    }

    fun stockSaved(stock: Stock) {
        viewModelScope.launch {
            getFavouritesUseCase.invoke().also { it ->
                _favStock.value = it.contains(stock)
            }
        }
    }
}




