package ar.team.stockify.ui.details

import androidx.lifecycle.*
import ar.team.stockify.domain.Stock
import ar.team.stockify.domain.StockDetail
import ar.team.stockify.domain.User
import ar.team.stockify.domain.StockDetail

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
        class IsFavourite(val state : Boolean) : UiDetailModel()
    }

    private val _model = MutableLiveData<UiDetailModel>()
    val model: LiveData<UiDetailModel>
        get() = _model

    private val _detailsQuarter = MutableLiveData<List<StockDetail>>()
    val detailsQuarter: LiveData<List<StockDetail>>
        get() = _detailsQuarter

/*
    private val _favStock = MutableLiveData<Boolean>()
    val favStock: LiveData<Boolean> = _favStock*/

    init {
        _model.value= UiDetailModel.Loading
    }


    fun onQueryCompanyDetails(stock: Stock) {

        viewModelScope.launch {
            val listDetail:List<StockDetail>? = getStockDetailUseCase.invoke(stock)
            checkIsFavourite(stock)
            val listDetail:List<StockDetail>? = getStockDetailUseCase.invoke(stock.name)
            Timber.d("${javaClass.simpleName} -> Network call to Get Company Overview Endpoint")
            println("TAG $listDetail")

            listDetail?.let { list ->
                if (listDetail.isNotEmpty()) {
                    _model.value = UiDetailModel.Content(list)
                }
                else
                    _model.value = UiDetailModel.NoContent

        }
    }

    private suspend fun checkIsFavourite(stock: Stock) {
            getFavouritesUseCase.invoke().also { it ->
                //_favStock.value = it.contains(stock)
                _model.value = UiDetailModel.IsFavourite(it.contains(stock))
            }
    }

    fun addRemoveFavourites(stock: Stock) {
        viewModelScope.launch {
            _model.value = UiDetailModel.IsFavourite(addRemoveFavUseCase.invoke(stock))
        }
    }
}




